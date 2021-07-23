
package br.com.orange.proposta.proposta.avisoviagem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.orange.proposta.proposta.avisoviagem.domain.AvisoViagem;

@Repository
public interface AvisoViagemRepository extends CrudRepository<AvisoViagem, Long> {

}
