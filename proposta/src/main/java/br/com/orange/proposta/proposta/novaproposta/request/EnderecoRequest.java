package br.com.orange.proposta.proposta.novaproposta.request;

import javax.validation.constraints.NotBlank;

import br.com.orange.proposta.proposta.novaproposta.domain.Endereco;

public class EnderecoRequest {

	@NotBlank
	private String logradouro;
	@NotBlank
	private String bairro;
	@NotBlank
	private String localidade;
	@NotBlank
	private String uf;
	@NotBlank
	private String cep;
	private String erro;

	@Deprecated
	public EnderecoRequest() {
	}

	public String getErro() {
		return erro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public String getUf() {
		return uf;
	}

	public String getCep() {
		return cep;
	}

	public Endereco toEntity(String complemento, Integer numero) {
		return new Endereco(logradouro, complemento, bairro, numero, localidade, uf, cep);
	}

}
