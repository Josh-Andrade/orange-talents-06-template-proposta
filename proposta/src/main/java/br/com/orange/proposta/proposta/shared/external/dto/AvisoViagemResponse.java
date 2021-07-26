package br.com.orange.proposta.proposta.shared.external.dto;

public class AvisoViagemResponse {

	private String resultado;

	@Deprecated
	public AvisoViagemResponse() {
	}

	public AvisoViagemResponse(String resultado) {
		this.resultado = resultado;
	}

	public String getResultado() {
		return resultado;
	}

}
