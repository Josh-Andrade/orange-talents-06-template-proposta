package br.com.orange.proposta.proposta.associarcarteira.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.orange.proposta.proposta.associarcarteira.domain.CartaoCarteira;
import br.com.orange.proposta.proposta.associarcarteira.domain.Carteira;

public class NovaAssociacaoCarteiraRequest {

	@NotBlank
	@Email
	private String email;
	@NotNull
	private Carteira carteira;

	public NovaAssociacaoCarteiraRequest(@NotBlank @Email String email, @NotNull Carteira carteira) {
		this.email = email;
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public Carteira getCarteira() {
		return carteira;
	}
	
	public CartaoCarteira toEntity(String numeroCartao) {
		return new CartaoCarteira(numeroCartao, email, carteira);
	}

}
