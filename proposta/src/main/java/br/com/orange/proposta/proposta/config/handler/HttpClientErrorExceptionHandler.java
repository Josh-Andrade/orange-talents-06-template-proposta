package br.com.orange.proposta.proposta.config.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class HttpClientErrorExceptionHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpClientErrorException.class)
	public String handler(HttpClientErrorException httpClientErrorException) {
		return httpClientErrorException.getLocalizedMessage();
	}
}
