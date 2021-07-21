package br.com.orange.proposta.proposta.novaproposta.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.orange.proposta.proposta.novaproposta.controller.dto.CartaoResponse;

@FeignClient(url = "${contas.host}", name = "cartao-resource")
public interface AssociarCartaoRequest {
	
	@GetMapping("/api/cartoes?idProposta={id}")
	public CartaoResponse associarCartao(@PathVariable String id);
		
	
}
