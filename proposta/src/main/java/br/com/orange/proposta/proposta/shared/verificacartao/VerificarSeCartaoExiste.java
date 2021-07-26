package br.com.orange.proposta.proposta.shared.verificacartao;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.orange.proposta.proposta.novaproposta.repository.PropostaRepository;

@Component
public class VerificarSeCartaoExiste implements CartaoEventos {

	private PropostaRepository propostaRepository;

	public VerificarSeCartaoExiste(PropostaRepository propostaRepository) {
		this.propostaRepository = propostaRepository;
	}

	@Override
	@Transactional
	public void verificarNumeroCartao(String numeroCartao) {
		if (propostaRepository.findByCartao_NumeroCartao(numeroCartao).isEmpty()) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
		
	}

}
