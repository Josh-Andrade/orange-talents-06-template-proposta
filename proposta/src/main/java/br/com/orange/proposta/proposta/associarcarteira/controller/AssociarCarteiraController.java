package br.com.orange.proposta.proposta.associarcarteira.controller;

import java.net.URI;
import java.util.Set;

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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.orange.proposta.proposta.associarcarteira.controller.dto.NovaAssociacaoCarteiraRequest;
import br.com.orange.proposta.proposta.associarcarteira.domain.CartaoCarteira;
import br.com.orange.proposta.proposta.associarcarteira.repository.CartaoCarteiraRepository;
import br.com.orange.proposta.proposta.shared.external.AssociarCartaoCarteiraRequest;
import br.com.orange.proposta.proposta.shared.verificacartao.CartaoEventos;
import feign.FeignException;

@RestController
@RequestMapping("/api")
public class AssociarCarteiraController {

	private CartaoCarteiraRepository cartaoCarteiraRepository;

	private Set<CartaoEventos> cartaoEventos;

	private AssociarCartaoCarteiraRequest associarCartaoCarteiraRequest;

	public AssociarCarteiraController(CartaoCarteiraRepository cartaoCarteiraRepository,
			Set<CartaoEventos> cartaoEventos, AssociarCartaoCarteiraRequest associarCartaoCarteiraRequest) {
		this.cartaoCarteiraRepository = cartaoCarteiraRepository;
		this.cartaoEventos = cartaoEventos;
		this.associarCartaoCarteiraRequest = associarCartaoCarteiraRequest;
	}

	@PostMapping("/cartao/carteira/{numeroCartao}")
	@Transactional
	public ResponseEntity<?> associarCarteira(@PathVariable("numeroCartao") String numeroCartao,
			@RequestBody @Valid NovaAssociacaoCarteiraRequest associacaoCarteiraRequest) {

		verificarRestricoesCartao(numeroCartao);
		verificarSeCartaoEstaAssociado(numeroCartao, associacaoCarteiraRequest);
		CartaoCarteira cartaoCarteira = associacaoCarteiraRequest.toEntity(numeroCartao);
		cartaoCarteira = associarCarteira(numeroCartao, associacaoCarteiraRequest, cartaoCarteira);

		URI uri = UriComponentsBuilder.fromPath("/api/cartao/carteira/{id}").buildAndExpand(cartaoCarteira.getId())
				.toUri();
		return ResponseEntity.created(uri).body(uri);
	}

	private CartaoCarteira associarCarteira(String numeroCartao,
			NovaAssociacaoCarteiraRequest associacaoCarteiraRequest, CartaoCarteira cartaoCarteira) {
		try {
			if (associarCartaoCarteiraRequest.associarCarteira(numeroCartao, associacaoCarteiraRequest).getResultado()
					.equals("ASSOCIADA")) {
				cartaoCarteira = cartaoCarteiraRepository.save(cartaoCarteira);
			}
		} catch (FeignException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Ocorreu um erro ao associar o cartão a carteira informada");
		}
		return cartaoCarteira;
	}

	private void verificarRestricoesCartao(String numeroCartao) {
		cartaoEventos.forEach(evento -> evento.verificarNumeroCartao(numeroCartao));
	}

	private void verificarSeCartaoEstaAssociado(String numeroCartao,
			NovaAssociacaoCarteiraRequest associacaoCarteiraRequest) {
		if (cartaoCarteiraRepository
				.findByNumeroCartaoAndCarteira(numeroCartao, associacaoCarteiraRequest.getCarteira()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Cartão já está associado a carteira informada");
		}
	}

}
