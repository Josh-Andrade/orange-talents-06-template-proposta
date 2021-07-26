package br.com.orange.proposta.proposta.shared.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.orange.proposta.proposta.associarcarteira.controller.dto.NovaAssociacaoCarteiraRequest;
import br.com.orange.proposta.proposta.shared.external.dto.AssociarCartaoCarteiraResponse;

@FeignClient(url = "${contas.host}", name = "associar-cartao-carteira-resource")
public interface AssociarCartaoCarteiraRequest {

	@PostMapping("/api/cartoes/{id}/carteiras")
	public AssociarCartaoCarteiraResponse associarCarteira(@PathVariable("id") String numeroCartao,
			@RequestBody NovaAssociacaoCarteiraRequest request);
}
