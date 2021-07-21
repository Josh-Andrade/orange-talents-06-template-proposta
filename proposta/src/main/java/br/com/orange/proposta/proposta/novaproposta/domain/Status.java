package br.com.orange.proposta.proposta.novaproposta.domain;

public enum Status {

	NAO_ELEGIVEL("COM_RESTRICAO"), ELEGIVEL("SEM_RESTRICAO");

	private String descricao;

	Status(String descricao) {
		this.descricao = descricao;
	}

	public static Status getStatusPorDescricao(String resultadoAnaliseFinanceira) {
		for(Status status : values()) {
			if(status.getDescricao().equals(resultadoAnaliseFinanceira)) {
				return status;
			}
		}
		return Status.NAO_ELEGIVEL;
	}

	public String getDescricao() {
		return descricao;
	}

}
