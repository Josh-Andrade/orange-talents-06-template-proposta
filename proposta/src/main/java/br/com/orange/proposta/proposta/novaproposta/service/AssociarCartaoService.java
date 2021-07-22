package br.com.orange.proposta.proposta.novaproposta.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.orange.proposta.proposta.novaproposta.controller.dto.CartaoResponse;
import br.com.orange.proposta.proposta.novaproposta.domain.Proposta;
import br.com.orange.proposta.proposta.novaproposta.domain.Status;
import br.com.orange.proposta.proposta.novaproposta.external.AssociarCartaoRequest;
import br.com.orange.proposta.proposta.novaproposta.repository.PropostaRepository;
import feign.FeignException;

@EnableAsync
@EnableScheduling
@Configuration
public class AssociarCartaoService {

	private PropostaRepository propostaRepository;

	private AssociarCartaoRequest associarCartaoRequest;

	public AssociarCartaoService(PropostaRepository propostaRepository, AssociarCartaoRequest associarCartaoRequest) {
		this.propostaRepository = propostaRepository;
		this.associarCartaoRequest = associarCartaoRequest;
	}

	@Scheduled(fixedDelay = 50000)
	@Transactional
	public void associarCartao() {
		List<Proposta> propostasElegiveis = propostaRepository.findByStatusAndCartao_NumeroCartaoIsNull(Status.ELEGIVEL);
		for (Proposta proposta : propostasElegiveis) {
			try {
				CartaoResponse cartaoResponse = associarCartaoRequest.associarCartao(proposta.getId().toString());
				proposta.setCartao(cartaoResponse.toEntity());
			} catch (FeignException e) {
				continue;
			}
		}
	}
}
