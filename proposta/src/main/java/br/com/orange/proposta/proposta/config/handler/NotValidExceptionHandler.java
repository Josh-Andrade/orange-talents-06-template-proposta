package br.com.orange.proposta.proposta.config.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotValidExceptionHandler {
	private MessageSource messageSource;

	public NotValidExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorResponse> handler(MethodArgumentNotValidException argumentNotValidException) {
		List<FieldError> fields = argumentNotValidException.getFieldErrors();
		List<ErrorResponse> errorDto = new ArrayList<>();
		fields.stream().forEach(f -> {
			errorDto.add(new ErrorResponse(getMessage(f), f.getField()));
		});
		return errorDto;
	}

	public String getMessage(FieldError field) {
		return messageSource.getMessage(field, LocaleContextHolder.getLocale());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public List<ErrorResponse> handlerIllegal(IllegalArgumentException illegalArgumentException) {
		String message = illegalArgumentException.getMessage();
		List<ErrorResponse> errorDto = new ArrayList<>();
		ErrorResponse errorResponse = new ErrorResponse(message, "");
		errorDto.add(errorResponse);
		return errorDto;
	}
}
