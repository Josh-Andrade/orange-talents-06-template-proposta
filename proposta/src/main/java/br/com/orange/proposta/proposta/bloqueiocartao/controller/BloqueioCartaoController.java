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

@RestController
@RequestMapping("/api")
public class BloqueioCartaoController {

	private PropostaRepository propostaRepository;

	private CartaoBloqueadoRepository cartaoBloqueadoRepository;

	public BloqueioCartaoController(PropostaRepository propostaRepository,
			CartaoBloqueadoRepository cartaoBloqueadoRepository) {
		this.propostaRepository = propostaRepository;
		this.cartaoBloqueadoRepository = cartaoBloqueadoRepository;
	}

	@PostMapping("/cartao/bloquear/{numeroCartao}")
	@Transactional
	public ResponseEntity<?> bloquearCartao(@PathVariable("numeroCartao") String numeroCartao,
			HttpServletRequest request) {
		verificarSeCartaoExiste(numeroCartao);
		verificarSeCartaoJaFoiBloqueado(numeroCartao);
		cartaoBloqueadoRepository
				.save(new CartaoBloqueado(numeroCartao, retornaUserAgent(request), retornaIp(request)));

		return ResponseEntity.ok().build();
	}

	private String retornaIp(HttpServletRequest request) {
		if (request.getHeader("X-Forwarded-For") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("X-Forwarded-For");
	}

	private String retornaUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	private void verificarSeCartaoJaFoiBloqueado(String numeroCartao) {
		if (cartaoBloqueadoRepository.findByNumeroCartao(numeroCartao).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já foi bloqueado");
	}

	private void verificarSeCartaoExiste(String numeroCartao) {
		if (propostaRepository.findByCartao_NumeroCartao(numeroCartao).isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
	}
}
