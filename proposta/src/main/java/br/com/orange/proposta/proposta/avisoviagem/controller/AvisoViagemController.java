package br.com.orange.proposta.proposta.avisoviagem.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.orange.proposta.proposta.avisoviagem.controller.dto.NovoAvisoViagemRequest;
import br.com.orange.proposta.proposta.avisoviagem.repository.AvisoViagemRepository;
import br.com.orange.proposta.proposta.shared.RequestUtils;
import br.com.orange.proposta.proposta.shared.verificacartao.CartaoEventos;

@RestController
@RequestMapping("/api")
public class AvisoViagemController {

	private AvisoViagemRepository avisoViagemRepository;

	private Set<CartaoEventos> cartaoEventos;

	public AvisoViagemController(AvisoViagemRepository avisoViagemRepository, Set<CartaoEventos> cartaoEventos) {
		this.avisoViagemRepository = avisoViagemRepository;
		this.cartaoEventos = cartaoEventos;
	}

	@PostMapping("/viagem/aviso/{numeroCartao}")
	public ResponseEntity<?> avisoViagem(@PathVariable("numeroCartao") String numeroCartao, HttpServletRequest request,
			@RequestBody @Valid NovoAvisoViagemRequest novoAvisoViagemRequest) {
		verificarRestricoesCartao(numeroCartao);
		avisoViagemRepository.save(novoAvisoViagemRequest.toEntity(numeroCartao, RequestUtils.retornaUserAgent(request),
				RequestUtils.retornaIp(request)));
		return ResponseEntity.ok().build();
	}

	private void verificarRestricoesCartao(String numeroCartao) {
		cartaoEventos.forEach(evento -> evento.verificarNumeroCartao(numeroCartao));
	}

}
