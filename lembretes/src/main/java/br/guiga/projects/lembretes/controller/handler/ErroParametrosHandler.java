package br.guiga.projects.lembretes.controller.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.guiga.projects.lembretes.controller.handler.dto.ErroDTO;

@RestControllerAdvice // Interceptador do JSON de erro de controllers REST
public class ErroParametrosHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST) // Devolve o 400 (erro total), não o 200 (deu certo)

	@ExceptionHandler(value = { MethodArgumentNotValidException.class }) // Tratador exceções de validação

	public List<ErroDTO> handle(MethodArgumentNotValidException e) { // Faz o tratamento de erro

		List<ErroDTO> errosdto = new ArrayList<ErroDTO>(); // Cria a lista de erros para enviar

		List<FieldError> erroscampo = e.getBindingResult().getFieldErrors(); // Pega os erros de campos

		erroscampo.forEach(exception -> { // Faz um forEach com lâmbida

			String mensagem = messageSource.getMessage(exception, LocaleContextHolder.getLocale());
			// Pega a mensagem da exceção e o locale e cria uma mensagem formatada

			ErroDTO dto = new ErroDTO(exception.getField(), mensagem);
			// Cria o erro formatado com o campo da exceceção e a mensagem formatada

			errosdto.add(dto); // coloca na lista para enviar
		});

		return errosdto; // Evia a lista de erros formatados
	}

}
