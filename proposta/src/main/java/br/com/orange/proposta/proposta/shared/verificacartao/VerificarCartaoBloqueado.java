package br.com.orange.proposta.proposta.shared.verificacartao;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.orange.proposta.proposta.bloqueiocartao.repository.CartaoBloqueadoRepository;

@Component
public class VerificarCartaoBloqueado implements CartaoEventos {

	private CartaoBloqueadoRepository cartaoBloqueadoRepository;

	public VerificarCartaoBloqueado(CartaoBloqueadoRepository cartaoBloqueadoRepository) {
		this.cartaoBloqueadoRepository = cartaoBloqueadoRepository;
	}

	@Override
	public void verificarNumeroCartao(String numeroCartao) {
		System.out.println("CARTAO BLOQUEADO");
		if (cartaoBloqueadoRepository.findByNumeroCartao(numeroCartao).isPresent()) {
			System.out.println("IF CARTAO BLOQUEADO");
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Este Cartão está bloqueado");
		}
	}

}
