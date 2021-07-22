package br.com.orange.proposta.proposta.bloqueiocartao.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


import br.com.orange.proposta.proposta.bloqueiocartao.domain.CartaoBloqueado;

public interface CartaoBloqueadoRepository extends CrudRepository<CartaoBloqueado, Long>{
	
	Optional<CartaoBloqueado> findByNumeroCartao(String numeroCartao);
}
