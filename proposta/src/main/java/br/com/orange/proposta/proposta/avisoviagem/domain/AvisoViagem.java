package br.com.orange.proposta.proposta.avisoviagem.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
public class AvisoViagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numeroCartao;
	@NotBlank
	private String destinoViagem;
	@NotNull
	@Future
	private LocalDate dataTermino;
	@PastOrPresent
	private LocalDateTime registradoEm;
	@NotBlank
	private String userAgent;
	@NotBlank
	private String ipCliente;

	public AvisoViagem(@NotBlank String numeroCartao, @NotBlank String destinoViagem,
			@NotNull @Future LocalDate dataTermino, String userAgent, String ipCliente) {
		this.numeroCartao = numeroCartao;
		this.destinoViagem = destinoViagem;
		this.dataTermino = dataTermino;
		this.registradoEm = LocalDateTime.now();
		this.userAgent = userAgent;
		this.ipCliente = ipCliente;
	}

}
