package br.com.orange.proposta.proposta.novaproposta.controller.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.orange.proposta.proposta.novaproposta.domain.Proposta;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long> {

	Optional<Proposta> findByDocumento(String documento);

}
