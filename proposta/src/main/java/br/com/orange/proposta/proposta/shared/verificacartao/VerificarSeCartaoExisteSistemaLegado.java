package br.com.orange.proposta.proposta.shared.verificacartao;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.orange.proposta.proposta.shared.external.VerificarCartaoRequest;

@Component
public class VerificarSeCartaoExisteSistemaLegado implements CartaoEventos {

	private VerificarCartaoRequest verificarCartaoRequest;

	public VerificarSeCartaoExisteSistemaLegado(VerificarCartaoRequest verificarCartaoRequest) {
		this.verificarCartaoRequest = verificarCartaoRequest;
	}

	@Override
	public void verificarNumeroCartao(String numeroCartao) {
		try {
			verificarCartaoRequest.verificarSeCartaoExiste(numeroCartao);
			System.out.println("TRY sistema legado");
		} catch (Exception e) {
			System.out.println("CATCH sistema legado");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
		}
	}

}
