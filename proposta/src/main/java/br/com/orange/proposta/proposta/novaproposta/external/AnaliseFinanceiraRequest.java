package br.com.orange.proposta.proposta.novaproposta.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.orange.proposta.proposta.novaproposta.controller.dto.AnaliseRequest;
import br.com.orange.proposta.proposta.novaproposta.controller.dto.AnaliseResponse;

@FeignClient(url = "http://localhost:9999", name = "solicitacao-analise-resource")
public interface AnaliseFinanceiraRequest {

	@PostMapping("/api/solicitacao")
	public AnaliseResponse analisarSolicitante(@RequestBody AnaliseRequest analiseRequest);
}
