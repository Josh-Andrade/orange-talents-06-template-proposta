package br.com.orange.proposta.proposta.config.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import br.com.orange.proposta.proposta.novaproposta.controller.dto.NovaPropostaRequest;
import br.com.orange.proposta.proposta.novaproposta.repository.PropostaRepository;

@Component
public class DocumentoPropostaValidator implements Validator {

	@Autowired
	private PropostaRepository propostaRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return NovaPropostaRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}
		
		NovaPropostaRequest request = (NovaPropostaRequest) target;
		TextEncryptor encryptor = Encryptors.queryableText("password", "5c0744940b5c369b");
		if (propostaRepository.findByDocumento(encryptor.encrypt(request.getDocumento())).isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Já existe uma proposta atribuida a essa documento");
		}
	}

}
