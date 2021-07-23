package br.com.orange.proposta.proposta.shared.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.orange.proposta.proposta.shared.external.dto.AnaliseRequest;
import br.com.orange.proposta.proposta.shared.external.dto.AnaliseResponse;

@FeignClient(url = "${analise.host}" , name = "solicitacao-analise-resource")
public interface AnaliseFinanceiraRequest {

	@PostMapping("/api/solicitacao")
	public AnaliseResponse analisarSolicitante(@RequestBody AnaliseRequest analiseRequest);
}
