package br.com.orange.proposta.proposta.shared.external.dto;

import javax.validation.constraints.NotBlank;

public class BloqueioCartaoResponse {

	@NotBlank
	private String resultado;

	@Deprecated
	public BloqueioCartaoResponse() {
	}
	
	public BloqueioCartaoResponse(String resultado) {
		this.resultado = resultado;
	}

	public String getResultado() {
		return resultado;
	}

}
