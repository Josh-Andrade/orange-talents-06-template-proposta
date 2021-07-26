package br.com.orange.proposta.proposta.shared.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.orange.proposta.proposta.avisoviagem.controller.dto.NovoAvisoViagemRequest;
import br.com.orange.proposta.proposta.shared.external.dto.AvisoViagemResponse;

@FeignClient(url = "${contas.host}", name = "notificar-viagem-resource")
public interface NotificarViagemRequest {

	@PostMapping("/api/cartoes/{id}/avisos")
	public AvisoViagemResponse notificarAvisoViagem(@PathVariable("id") String numeroCartao,
			@RequestBody NovoAvisoViagemRequest request);
}
