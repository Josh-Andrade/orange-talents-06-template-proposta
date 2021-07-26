package br.com.orange.proposta.proposta.criarbiometria.controller;

import static br.com.orange.proposta.proposta.shared.Constants.BIOMETRIA_PATH;

import java.net.URI;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.orange.proposta.proposta.criarbiometria.controller.dto.BiometriaRequest;
import br.com.orange.proposta.proposta.criarbiometria.domain.Biometria;
import br.com.orange.proposta.proposta.criarbiometria.repository.BiometriaRepository;
import br.com.orange.proposta.proposta.shared.verificacartao.CartaoEventos;

@RestController
@RequestMapping("/api")
public class CriarBiometriaController {

	private BiometriaRepository biometriaRepository;

	private Set<CartaoEventos> cartaoEventos;

	public CriarBiometriaController(BiometriaRepository biometriaRepository, Set<CartaoEventos> cartaoEventos) {
		this.biometriaRepository = biometriaRepository;
		this.cartaoEventos = cartaoEventos;
	}

	@PostMapping("/cartao/biometria/{numeroCartao}")
	@Transactional
	public ResponseEntity<?> criarBiometria(@PathVariable("numeroCartao") String numeroCartao,
			@Valid @RequestBody BiometriaRequest request, UriComponentsBuilder uriBuilder) {

		verificarRestricoesCartao(numeroCartao);
		Biometria biometria = biometriaRepository.save(request.toEntity(numeroCartao));
		URI uri = UriComponentsBuilder.fromPath(BIOMETRIA_PATH).buildAndExpand(biometria.getId()).toUri();
		return ResponseEntity.created(uri).body(uri);
	}

	private void verificarRestricoesCartao(String numeroCartao) {
		cartaoEventos.forEach(evento -> evento.verificarNumeroCartao(numeroCartao));
	}

}
