package br.com.orange.proposta.proposta.shared.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.orange.proposta.proposta.shared.external.dto.SistemaResponsavelRequest;

@FeignClient(url = "${contas.host}", name = "bloquear-cartao-resource")
public interface BloquearCartaoRequest {

	@PostMapping("/api/cartoes/{id}/bloqueios")
	public String bloquearCartao(@PathVariable("id") String numeroCartao, @RequestBody SistemaResponsavelRequest sistema);
}
