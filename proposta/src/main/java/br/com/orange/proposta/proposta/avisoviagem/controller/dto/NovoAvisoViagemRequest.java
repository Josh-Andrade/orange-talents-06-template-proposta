package br.com.orange.proposta.proposta.avisoviagem.controller.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.orange.proposta.proposta.avisoviagem.domain.AvisoViagem;

public class NovoAvisoViagemRequest {
	
	@NotBlank
	private String destinoViagem;
	@NotNull
	@Future
	private LocalDate dataTermino;
	
	public NovoAvisoViagemRequest(String destinoViagem, LocalDate dataTermino) {
		this.destinoViagem = destinoViagem;
		this.dataTermino = dataTermino;
	}
	
	public AvisoViagem toEntity(String numeroCartao, String userAgent, String ipCliente) {
		Assert.isTrue(dataTermino.isAfter(LocalDate.now()), "Data final da viagem deve ser futura");
		return new AvisoViagem(numeroCartao, destinoViagem, dataTermino, userAgent, ipCliente);
	}
}
