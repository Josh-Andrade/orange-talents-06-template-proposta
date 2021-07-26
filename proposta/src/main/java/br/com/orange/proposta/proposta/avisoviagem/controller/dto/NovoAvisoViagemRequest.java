package br.com.orange.proposta.proposta.avisoviagem.controller.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.orange.proposta.proposta.avisoviagem.domain.AvisoViagem;

public class NovoAvisoViagemRequest {

	@NotBlank
	private String destino;
	@NotNull
	@Future
	private LocalDate validoAte;

	public NovoAvisoViagemRequest(String destino, LocalDate validoAte) {
		this.destino = destino;
		this.validoAte = validoAte;
	}

	public AvisoViagem toEntity(String numeroCartao, String userAgent, String ipCliente) {
		Assert.isTrue(validoAte.isAfter(LocalDate.now()), "Data final da viagem deve ser futura");
		return new AvisoViagem(numeroCartao, destino, validoAte, userAgent, ipCliente);
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getValidoAte() {
		return validoAte;
	}

}
