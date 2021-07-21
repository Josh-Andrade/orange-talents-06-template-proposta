package br.com.orange.proposta.proposta.acompanhamentoproposta.controller.dto;

public class EnderecoResponse {

	private String logradouro;
	private String complemento;
	private String bairro;
	private Integer numero;

	public EnderecoResponse(String logradouro, String complemento, String bairro, Integer numero) {
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public Integer getNumero() {
		return numero;
	}

}
