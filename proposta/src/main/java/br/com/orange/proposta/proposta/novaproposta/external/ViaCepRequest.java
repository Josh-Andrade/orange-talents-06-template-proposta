package br.com.orange.proposta.proposta.novaproposta.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.orange.proposta.proposta.novaproposta.controller.dto.EnderecoRequest;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface ViaCepRequest {

	@GetMapping("{cep}/json")
	public EnderecoRequest buscaEnderecoPorCep(@PathVariable("cep") String cep);
}
