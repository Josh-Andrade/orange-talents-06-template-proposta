package br.com.orange.proposta.proposta.criarbiometria.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String biometria;
	@NotBlank
	private String numeroCartao;

	@Deprecated
	public Biometria() {
	}

	public Biometria(String biometria, String numeroCartao) {
		this.biometria = biometria;
		this.numeroCartao = numeroCartao;
	}

	public Long getId() {
		return id;
	}

}
