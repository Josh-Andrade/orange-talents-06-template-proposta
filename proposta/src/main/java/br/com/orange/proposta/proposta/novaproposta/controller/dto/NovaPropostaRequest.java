package br.com.orange.proposta.proposta.novaproposta.controller.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.orange.proposta.proposta.config.validation.CPFOrCNPJ;
import br.com.orange.proposta.proposta.novaproposta.domain.Proposta;

public class NovaPropostaRequest {

	@CPFOrCNPJ
	@NotBlank
	private String documento;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String nome;
	@NotBlank
	private String cep;

	private String complemento;
	@NotNull
	private Integer numero;
	@NotNull
	@Positive
	private BigDecimal salario;

	public NovaPropostaRequest(@NotBlank String documento, @Email @NotBlank String email, @NotBlank String nome,
			@NotBlank String cep, @NotNull Integer numero, @NotNull @Positive BigDecimal salario) {
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.cep = cep;
		this.numero = numero;
		this.salario = salario;
	}

	public String getDocumento() {
		return documento;
	}

	public String getCep() {
		return cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getEmail() {
		return email;
	}

	public Proposta toEntity(EnderecoRequest endereco) {
		return new Proposta(documento, email, nome, salario, endereco.toEntity(complemento, numero));
	}

}
