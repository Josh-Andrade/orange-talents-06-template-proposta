package br.com.orange.proposta.proposta.config.handler;

public class ErrorResponse {

	private String mensagem;
	private String campo;

	public ErrorResponse(String mensagem, String campo) {
		this.mensagem = mensagem;
		this.campo = campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getCampo() {
		return campo;
	}

}
