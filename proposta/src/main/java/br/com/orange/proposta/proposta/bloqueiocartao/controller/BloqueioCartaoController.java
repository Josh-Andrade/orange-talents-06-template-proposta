package br.com.orange.proposta.proposta.bloqueiocartao.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.orange.proposta.proposta.bloqueiocartao.domain.CartaoBloqueado;
import br.com.orange.proposta.proposta.bloqueiocartao.repository.CartaoBloqueadoRepository;
import br.com.orange.proposta.proposta.shared.RequestUtils;
import br.com.orange.proposta.proposta.shared.external.BloquearCartaoRequest;
import br.com.orange.proposta.proposta.shared.external.dto.SistemaResponsavelRequest;
import br.com.orange.proposta.proposta.shared.verificacartao.CartaoEventos;
import feign.FeignException;

@RestController
@RequestMapping("/api")
public class BloqueioCartaoController {

	private CartaoBloqueadoRepository cartaoBloqueadoRepository;

	private BloquearCartaoRequest bloquearCartaoRequest;

	private Set<CartaoEventos> cartaoEventos;

	public BloqueioCartaoController(CartaoBloqueadoRepository cartaoBloqueadoRepository,
			BloquearCartaoRequest bloquearCartaoRequest, Set<CartaoEventos> cartaoEventos) {
		this.cartaoBloqueadoRepository = cartaoBloqueadoRepository;
		this.bloquearCartaoRequest = bloquearCartaoRequest;
		this.cartaoEventos = cartaoEventos;
	}

	@PostMapping("/cartao/bloquear/{numeroCartao}")
	@Transactional
	public ResponseEntity<?> bloquearCartao(@PathVariable("numeroCartao") String numeroCartao,
			HttpServletRequest request) {

		verificarRestricoesCartao(numeroCartao);
		notificarSistemaLegado(numeroCartao, request);

		return ResponseEntity.ok().build();
	}

	private void verificarRestricoesCartao(String numeroCartao) {
		cartaoEventos.forEach(evento -> evento.verificarNumeroCartao(numeroCartao));
	}

	private void notificarSistemaLegado(String numeroCartao, HttpServletRequest request) {
		try {
			if (bloquearCartaoRequest.bloquearCartao(numeroCartao, new SistemaResponsavelRequest("Proposta"))
					.getResultado().equals("BLOQUEADO"))
				cartaoBloqueadoRepository.save(new CartaoBloqueado(numeroCartao, RequestUtils.retornaUserAgent(request),
						RequestUtils.retornaIp(request)));
		} catch (FeignException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Ocorreu um erro ao tentar bloquear o cartão");
		}
	}
}
