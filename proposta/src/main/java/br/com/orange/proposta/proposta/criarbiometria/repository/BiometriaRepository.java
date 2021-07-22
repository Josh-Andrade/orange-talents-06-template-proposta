package br.com.orange.proposta.proposta.criarbiometria.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.orange.proposta.proposta.criarbiometria.domain.Biometria;

@Repository
public interface BiometriaRepository extends CrudRepository<Biometria, Long>{

}
