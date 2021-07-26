package br.com.orange.proposta.proposta.associarcarteira.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.orange.proposta.proposta.associarcarteira.domain.CartaoCarteira;
import br.com.orange.proposta.proposta.associarcarteira.domain.Carteira;

public interface CartaoCarteiraRepository extends CrudRepository<CartaoCarteira, Long>{

	Optional<CartaoCarteira> findByNumeroCartaoAndCarteira(String numeroCartao, Carteira carteira);

}
