package br.com.orange.proposta.proposta.shared.external.dto;

import br.com.orange.proposta.proposta.novaproposta.domain.Proposta;

public class AnaliseRequest {

	private String documento;
	private String nome;
	private String idProposta;

	public AnaliseRequest(Proposta proposta) {
		this.documento = proposta.getDocumento();
		this.nome = proposta.getNome();
		this.idProposta = proposta.getId().toString();
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public String getIdProposta() {
		return idProposta;
	}

}
