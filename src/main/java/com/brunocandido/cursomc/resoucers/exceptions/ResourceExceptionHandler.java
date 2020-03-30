package com.brunocandido.cursomc.resoucers.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.brunocandido.cursomc.exceptions.ObjectNotFoundException;

//Esta Classe de Excessão serve para retornar do Jason um erro caso o usuario digite uma pagina não existente por exemplo
//Esta classe é o elo de ligação entre a tratativa de erro ObjectNotFoundException e a WEB

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class) //Tratador de excessoes dentro do objeto ObjectNotFoudException
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
