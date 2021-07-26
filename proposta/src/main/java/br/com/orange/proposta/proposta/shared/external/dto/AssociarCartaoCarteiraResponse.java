package br.com.orange.proposta.proposta.shared.external.dto;

public class AssociarCartaoCarteiraResponse {

	private String resultado;

	@Deprecated
	public AssociarCartaoCarteiraResponse() {
	}

	public AssociarCartaoCarteiraResponse(String resultado) {
		this.resultado = resultado;
	}

	public String getResultado() {
		return resultado;
	}

}
