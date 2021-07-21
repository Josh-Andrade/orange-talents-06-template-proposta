package br.com.orange.proposta.proposta.novaproposta.domain;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Cartao {

	private String numeroCartao;
	private BigDecimal limite;

	@Deprecated
	public Cartao() {
	}
	
	public Cartao(String id, BigDecimal limite) {
		this.numeroCartao = id;
		this.limite = limite;
	}

}
