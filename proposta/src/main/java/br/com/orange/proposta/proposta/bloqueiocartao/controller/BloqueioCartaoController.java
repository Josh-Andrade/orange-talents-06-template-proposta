package br.com.orange.proposta.proposta.bloqueiocartao.controller;

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
import br.com.orange.proposta.proposta.novaproposta.repository.PropostaRepository;
import br.com.orange.proposta.proposta.shared.external.BloquearCartaoRequest;
import br.com.orange.proposta.proposta.shared.external.VerificarCartaoRequest;
import br.com.orange.proposta.proposta.shared.external.dto.SistemaResponsavelRequest;

@RestController
@RequestMapping("/api")
public class BloqueioCartaoController {

	private PropostaRepository propostaRepository;

	private CartaoBloqueadoRepository cartaoBloqueadoRepository;

	private BloquearCartaoRequest bloquearCartaoRequest;

	private VerificarCartaoRequest verificarCartaoRequest;

	public BloqueioCartaoController(PropostaRepository propostaRepository,
			CartaoBloqueadoRepository cartaoBloqueadoRepository, BloquearCartaoRequest bloquearCartaoRequest,
			VerificarCartaoRequest verificarCartaoRequest) {
		this.propostaRepository = propostaRepository;
		this.cartaoBloqueadoRepository = cartaoBloqueadoRepository;
		this.bloquearCartaoRequest = bloquearCartaoRequest;
		this.verificarCartaoRequest = verificarCartaoRequest;
	}

	@PostMapping("/cartao/bloquear/{numeroCartao}")
	@Transactional
	public ResponseEntity<?> bloquearCartao(@PathVariable("numeroCartao") String numeroCartao,
			HttpServletRequest request) {

		verificarSeCartaoExiste(numeroCartao);
		verificarSeCartaoExisteSistemaLegado(numeroCartao);
		verificarSeCartaoJaFoiBloqueado(numeroCartao);
		notificarSistemaLegado(numeroCartao, request);

		return ResponseEntity.ok().build();
	}

	private void verificarSeCartaoExisteSistemaLegado(String numeroCartao) {
		try {
			verificarCartaoRequest.verificarSeCartaoExiste(numeroCartao);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
		}
	}

	private void verificarSeCartaoJaFoiBloqueado(String numeroCartao) {
		if (cartaoBloqueadoRepository.findByNumeroCartao(numeroCartao).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já foi bloqueado");
	}

	private void verificarSeCartaoExiste(String numeroCartao) {
		if (propostaRepository.findByCartao_NumeroCartao(numeroCartao).isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
	}

	private void notificarSistemaLegado(String numeroCartao, HttpServletRequest request) {
		try {
			if (bloquearCartaoRequest.bloquearCartao(numeroCartao,
					new SistemaResponsavelRequest("Proposta")) == "BLOQUEADO")
				cartaoBloqueadoRepository
						.save(new CartaoBloqueado(numeroCartao, retornaUserAgent(request), retornaIp(request)));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ocorreu um erro ao tentar bloquear o cartão");
		}
	}

	private String retornaIp(HttpServletRequest request) {
		return request.getHeader("X-Forwarded-For") != null ? request.getHeader("X-Forwarded-For")
				: request.getRemoteAddr();
	}

	private String retornaUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}
}
