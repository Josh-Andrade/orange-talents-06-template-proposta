package br.com.orange.proposta.proposta.shared.external.dto;

import java.math.BigDecimal;

import br.com.orange.proposta.proposta.novaproposta.domain.Cartao;

public class CartaoResponse {

	private String id;
	private BigDecimal limite;

	public CartaoResponse(String id, BigDecimal limite) {
		this.id = id;
		this.limite = limite;
	}

	public String getId() {
		return id;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public Cartao toEntity() {
		return new Cartao(id, limite);
	}
}
