package br.com.orange.proposta.proposta.criarbiometria.controller.dto;

import javax.validation.constraints.NotBlank;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Assert;

import br.com.orange.proposta.proposta.criarbiometria.domain.Biometria;

public class BiometriaRequest {

	@NotBlank
	private String biometria;

	@Deprecated
	public BiometriaRequest() {
	}

	public BiometriaRequest(@NotBlank String biometria) {
		this.biometria = biometria;
	}

	public String getBiometria() {
		return biometria;
	}

	public Biometria toEntity(String numeroCartao) {
		Assert.isTrue(Base64.isBase64(biometria), "A biometria deve ser enviada em BASE64");
		return new Biometria(biometria, numeroCartao);
	}
}
