package br.com.orange.proposta.proposta.associarcarteira.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CartaoCarteira {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numeroCartao;
	@NotBlank
	@Email
	private String email;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Carteira carteira;

	@Deprecated
	public CartaoCarteira() {
	}
	
	public CartaoCarteira(String numeroCartao, @NotBlank @Email String email, @NotNull Carteira carteira) {
		this.numeroCartao = numeroCartao;
		this.email = email;
		this.carteira = carteira;
	}

	public Long getId() {
		return id;
	}

}
