package br.com.orange.proposta.proposta.acompanhamentoproposta.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.orange.proposta.proposta.acompanhamentoproposta.controller.dto.PropostaResponse;
import br.com.orange.proposta.proposta.novaproposta.domain.Proposta;
import br.com.orange.proposta.proposta.novaproposta.repository.PropostaRepository;

@RestController
@RequestMapping("/api")
public class AcompanhamentoPropostaController {

	private PropostaRepository propostaRepository;

	public AcompanhamentoPropostaController(PropostaRepository propostaRepository) {
		this.propostaRepository = propostaRepository;
	}

	@GetMapping("/proposta/{id}")
	public ResponseEntity<PropostaResponse> acompanharProposta(@PathVariable Long id) {
		Optional<Proposta> optionalProposta = propostaRepository.findById(id);
		if(optionalProposta.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(optionalProposta.get().toResponse());
	}
}
