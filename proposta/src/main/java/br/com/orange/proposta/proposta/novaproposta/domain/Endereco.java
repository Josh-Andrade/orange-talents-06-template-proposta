package br.com.orange.proposta.proposta.novaproposta.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
public class Endereco {
	
	@NotBlank
	private String logradouro;

	@SuppressWarnings("unused")
	private String complemento;
	@NotBlank
	private String bairro;
	@NotNull
	private Integer numero;
	@NotBlank
	private String localidade;
	@NotBlank
	private String uf;
	@NotBlank
	private String cep;
	
	@Deprecated
	public Endereco() {
	}
	
	public Endereco(@NotBlank String logradouro, @NotBlank String complemento, @NotBlank String bairro,
			@NotBlank @NotNull Integer numero, @NotBlank String localidade, @NotBlank String uf, @NotBlank String cep) {
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.numero = numero;
		this.localidade = localidade;
		this.uf = uf;
		this.cep = cep;
	}
	
	
}
