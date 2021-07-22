package br.com.orange.proposta.proposta.bloqueiocartao.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CartaoBloqueado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numeroCartao;
	@NotBlank
	private String retornaUserAgent;
	@NotBlank
	private String retornaIp;
	@NotNull
	private LocalDateTime bloqueadoEm;

	@Deprecated
	public CartaoBloqueado() {
	}
	
	public CartaoBloqueado(String numeroCartao, String retornaUserAgent, String retornaIp) {
		this.numeroCartao = numeroCartao;
		this.retornaUserAgent = retornaUserAgent;
		this.retornaIp = retornaIp;
		this.bloqueadoEm = LocalDateTime.now();
	}

}
