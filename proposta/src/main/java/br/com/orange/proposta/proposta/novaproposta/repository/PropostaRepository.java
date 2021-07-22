package br.com.orange.proposta.proposta.novaproposta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.orange.proposta.proposta.novaproposta.domain.Proposta;
import br.com.orange.proposta.proposta.novaproposta.domain.Status;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long> {

	Optional<Proposta> findByDocumento(String documento);

	List<Proposta> findByStatusAndCartao_NumeroCartaoIsNull(Status status);

	Optional<Proposta> findByCartao_NumeroCartao(String numeroCartao);
}
