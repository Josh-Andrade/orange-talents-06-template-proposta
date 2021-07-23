package br.com.orange.proposta.proposta.shared.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.orange.proposta.proposta.shared.external.dto.CartaoResponse;

@FeignClient(url = "${contas.host}", name = "verificar-cartao-resource")
public interface VerificarCartaoRequest {

	@GetMapping("/api/cartoes/{id}")
	public CartaoResponse verificarSeCartaoExiste(@PathVariable("id") String numeroCartao); 
}
