package br.com.orange.proposta.proposta.acompanhamentoproposta.controller.dto;

import br.com.orange.proposta.proposta.novaproposta.domain.Status;
import br.com.orange.proposta.proposta.shared.external.dto.CartaoResponse;

public class PropostaResponse {

	private String documento;
	private String nome;
	private CartaoResponse cartaoResponse;
	private EnderecoResponse enderecoResponse;
	private Status status;

	public PropostaResponse(String documento, String nome, CartaoResponse cartaoResponse,
			EnderecoResponse enderecoResponse, Status status) {
		this.documento = documento;
		this.nome = nome;
		this.cartaoResponse = cartaoResponse;
		this.enderecoResponse = enderecoResponse;
		this.status = status;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public CartaoResponse getCartaoResponse() {
		return cartaoResponse;
	}

	public EnderecoResponse getEnderecoResponse() {
		return enderecoResponse;
	}

	public Status getStatus() {
		return status;
	}

	
	
}
