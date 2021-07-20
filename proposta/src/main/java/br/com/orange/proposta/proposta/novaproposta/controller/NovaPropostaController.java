package br.com.orange.proposta.proposta.novaproposta.controller;

import static br.com.orange.proposta.proposta.shared.Constants.PROPOSTA_PATH;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.orange.proposta.proposta.novaproposta.controller.repository.PropostaRepository;
import br.com.orange.proposta.proposta.novaproposta.domain.Proposta;
import br.com.orange.proposta.proposta.novaproposta.external.ViaCepRequest;
import br.com.orange.proposta.proposta.novaproposta.request.EnderecoRequest;
import br.com.orange.proposta.proposta.novaproposta.request.NovaPropostaRequest;
import feign.FeignException;

@RestController
@RequestMapping("/api")
public class NovaPropostaController {
	
	private ViaCepRequest viaCepRequest;

	private PropostaRepository propostaRepository;

	public NovaPropostaController(ViaCepRequest viaCepRequest, PropostaRepository propostaRepository) {
		this.viaCepRequest = viaCepRequest;
		this.propostaRepository = propostaRepository;
	}

	@PostMapping("/proposta")
	public ResponseEntity<?> criarNovaProposta(@RequestBody @Valid NovaPropostaRequest request,
			UriComponentsBuilder uriBuilder) {
		EnderecoRequest buscarEnderecoViaCep = buscarEndereco(request.getCep());
		if (Boolean.parseBoolean(buscarEnderecoViaCep.getErro()))
			return ResponseEntity.badRequest().body("Não foi encontrado nenhum endereço com o cep informado");
		
		Proposta proposta = propostaRepository.save(request.toEntity(buscarEnderecoViaCep));
		
		URI uri = UriComponentsBuilder.fromPath(PROPOSTA_PATH).buildAndExpand(proposta.getId()).toUri();
		return ResponseEntity.created(uri).body(uri);
	}
	
	private EnderecoRequest buscarEndereco(String cep) {
		try {
			return viaCepRequest.buscaEnderecoPorCep(cep);
		} catch (FeignException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Formato do Cep invalido");
		}
	}
}
