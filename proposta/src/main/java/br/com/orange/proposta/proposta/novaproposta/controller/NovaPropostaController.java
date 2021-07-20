package br.com.orange.proposta.proposta.novaproposta.controller;

import static br.com.orange.proposta.proposta.shared.Constants.PROPOSTA_PATH;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.orange.proposta.proposta.config.validation.DocumentoPropostaValidator;
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

	private DocumentoPropostaValidator documentoPropostaValidator;

	public NovaPropostaController(ViaCepRequest viaCepRequest, PropostaRepository propostaRepository,
			DocumentoPropostaValidator documentoPropostaValidator) {
		this.viaCepRequest = viaCepRequest;
		this.propostaRepository = propostaRepository;
		this.documentoPropostaValidator = documentoPropostaValidator;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(documentoPropostaValidator);
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
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato do Cep invalido");
		}
	}
}
