package br.com.orange.proposta.proposta.novaproposta.controller;

import static br.com.orange.proposta.proposta.shared.Constants.PROPOSTA_PATH;
import static br.com.orange.proposta.proposta.shared.Constants.SPAN_EMAIL_TAG;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.orange.proposta.proposta.config.validation.DocumentoPropostaValidator;
import br.com.orange.proposta.proposta.novaproposta.controller.dto.EnderecoRequest;
import br.com.orange.proposta.proposta.novaproposta.controller.dto.NovaPropostaRequest;
import br.com.orange.proposta.proposta.novaproposta.domain.Proposta;
import br.com.orange.proposta.proposta.novaproposta.domain.Status;
import br.com.orange.proposta.proposta.novaproposta.repository.PropostaRepository;
import br.com.orange.proposta.proposta.shared.external.AnaliseFinanceiraRequest;
import br.com.orange.proposta.proposta.shared.external.ViaCepRequest;
import br.com.orange.proposta.proposta.shared.external.dto.AnaliseRequest;
import br.com.orange.proposta.proposta.shared.external.dto.AnaliseResponse;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
@RequestMapping("/api")
public class NovaPropostaController {

	private ViaCepRequest viaCepRequest;

	private PropostaRepository propostaRepository;

	private DocumentoPropostaValidator documentoPropostaValidator;

	private AnaliseFinanceiraRequest analiseFinanceiraRequest;

	private final Tracer tracer;

	public NovaPropostaController(ViaCepRequest viaCepRequest, PropostaRepository propostaRepository,
			DocumentoPropostaValidator documentoPropostaValidator, AnaliseFinanceiraRequest analiseFinanceiraRequest,
			Tracer tracer) {
		this.viaCepRequest = viaCepRequest;
		this.propostaRepository = propostaRepository;
		this.documentoPropostaValidator = documentoPropostaValidator;
		this.analiseFinanceiraRequest = analiseFinanceiraRequest;
		this.tracer = tracer;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(documentoPropostaValidator);
	}

	@PostMapping("/proposta")
	@Transactional
	public ResponseEntity<?> criarNovaProposta(@RequestBody @Valid NovaPropostaRequest request) {

		EnderecoRequest buscarEnderecoViaCep = buscarEndereco(request.getCep());

		Assert.isTrue(!Boolean.parseBoolean(buscarEnderecoViaCep.getErro()),
				"Não foi encontrado nenhum endereço com o cep informado");

		Proposta proposta = propostaRepository.save(request.toEntity(buscarEnderecoViaCep));
		URI uri = UriComponentsBuilder.fromPath(PROPOSTA_PATH).buildAndExpand(proposta.getId()).toUri();
		associarEmailAoBaggage(request);
		proposta.setStatus(analiseFinanceiraSolicitante(proposta));
		return ResponseEntity.created(uri).body(uri);
	}

	private void associarEmailAoBaggage(NovaPropostaRequest request) {
		Span activeSpan = tracer.activeSpan();
		activeSpan.setBaggageItem(SPAN_EMAIL_TAG, request.getEmail());
	}

	private Status analiseFinanceiraSolicitante(Proposta proposta) {
		try {
			AnaliseResponse analiseResponse = analiseFinanceiraRequest
					.analisarSolicitante(new AnaliseRequest(proposta));
			return Status.getStatusPorDescricao(analiseResponse.getResultadoSolicitacao());
		} catch (FeignException e) {
			return Status.NAO_ELEGIVEL;
		}
	}

	private EnderecoRequest buscarEndereco(String cep) {
		try {
			return viaCepRequest.buscaEnderecoPorCep(cep);
		} catch (FeignException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato do Cep invalido");
		}
	}
}
