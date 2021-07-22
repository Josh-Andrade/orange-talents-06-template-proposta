package br.com.orange.proposta.proposta.criarbiometria.controller;

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

import br.com.orange.proposta.proposta.criarbiometria.controller.dto.BiometriaRequest;
import br.com.orange.proposta.proposta.criarbiometria.domain.Biometria;
import br.com.orange.proposta.proposta.criarbiometria.repository.BiometriaRepository;
import br.com.orange.proposta.proposta.novaproposta.repository.PropostaRepository;

import static br.com.orange.proposta.proposta.shared.Constants.BIOMETRIA_PATH;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class CriarBiometriaController {

	private PropostaRepository propostaRepository;

	private BiometriaRepository biometriaRepository;

	public CriarBiometriaController(PropostaRepository propostaRepository, BiometriaRepository biometriaRepository) {
		this.propostaRepository = propostaRepository;
		this.biometriaRepository = biometriaRepository;
	}

	@PostMapping("/biometria/{numeroCartao}")
	@Transactional
	public ResponseEntity<?> criarBiometria(@PathVariable("numeroCartao") String numeroCartao,
			@Valid @RequestBody BiometriaRequest request, UriComponentsBuilder uriBuilder) {

		if (validarNumeroCartao(numeroCartao))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");

		Biometria biometria = biometriaRepository.save(request.toEntity(numeroCartao));
		URI uri = UriComponentsBuilder.fromPath(BIOMETRIA_PATH).buildAndExpand(biometria.getId()).toUri();
		
		return ResponseEntity.created(uri).body(uri);
	}

	private boolean validarNumeroCartao(String numeroCartao) {
		return propostaRepository.findByCartao_NumeroCartao(numeroCartao).isEmpty();
	}
}
