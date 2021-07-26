package br.com.orange.proposta.proposta.avisoviagem.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.orange.proposta.proposta.avisoviagem.controller.dto.NovoAvisoViagemRequest;
import br.com.orange.proposta.proposta.avisoviagem.repository.AvisoViagemRepository;
import br.com.orange.proposta.proposta.shared.RequestUtils;
import br.com.orange.proposta.proposta.shared.external.NotificarViagemRequest;
import br.com.orange.proposta.proposta.shared.verificacartao.CartaoEventos;
import feign.FeignException;

@RestController
@RequestMapping("/api")
public class AvisoViagemController {

	private AvisoViagemRepository avisoViagemRepository;

	private Set<CartaoEventos> cartaoEventos;

	private NotificarViagemRequest notificarViagemRequest;

	public AvisoViagemController(AvisoViagemRepository avisoViagemRepository, Set<CartaoEventos> cartaoEventos,
			NotificarViagemRequest notificarViagemRequest) {
		this.avisoViagemRepository = avisoViagemRepository;
		this.cartaoEventos = cartaoEventos;
		this.notificarViagemRequest = notificarViagemRequest;
	}

	@PostMapping("/cartao/viagem/aviso/{numeroCartao}")
	@Transactional
	public ResponseEntity<?> avisoViagem(@PathVariable("numeroCartao") String numeroCartao, HttpServletRequest request,
			@RequestBody @Valid NovoAvisoViagemRequest novoAvisoViagemRequest) {

		verificarRestricoesCartao(numeroCartao);
		notificarSistemaLegado(numeroCartao, request, novoAvisoViagemRequest);

		return ResponseEntity.ok().build();
	}

	private void notificarSistemaLegado(String numeroCartao, HttpServletRequest request,
			NovoAvisoViagemRequest novoAvisoViagemRequest) {
		try {
			if (notificarViagemRequest.notificarAvisoViagem(numeroCartao, novoAvisoViagemRequest).getResultado()
					.equals("CRIADO"))
				avisoViagemRepository.save(novoAvisoViagemRequest.toEntity(numeroCartao,
						RequestUtils.retornaUserAgent(request), RequestUtils.retornaIp(request)));

		} catch (FeignException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao tentar notificar a viagem");
		}
	}

	private void verificarRestricoesCartao(String numeroCartao) {
		cartaoEventos.forEach(evento -> evento.verificarNumeroCartao(numeroCartao));
	}

}
