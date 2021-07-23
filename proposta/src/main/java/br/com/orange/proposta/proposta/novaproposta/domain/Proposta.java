package br.com.orange.proposta.proposta.novaproposta.domain;

import java.math.BigDecimal;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.orange.proposta.proposta.acompanhamentoproposta.controller.dto.PropostaResponse;
import br.com.orange.proposta.proposta.config.validation.CPFOrCNPJ;
import br.com.orange.proposta.proposta.shared.external.dto.CartaoResponse;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CPFOrCNPJ
	@NotBlank
	private String documento;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String nome;
	@NotNull
	@Positive
	private BigDecimal salario;
	@NotNull
	@Embedded
	private Endereco endereco;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Embedded
	private Cartao cartao;
	
	@Deprecated
	public Proposta() {
	}

	public Proposta(@NotBlank String documento, @Email @NotBlank String email, @NotBlank String nome,
			@NotNull @Positive BigDecimal salario, @NotNull Endereco endereco) {
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.salario = salario;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	
	public PropostaResponse toResponse() {
		return new PropostaResponse(documento, nome, verificarCartao(), endereco.toResponse(), status);
	}

	private CartaoResponse verificarCartao() {
		return cartao == null ? null : cartao.toResponse();
	}

}
