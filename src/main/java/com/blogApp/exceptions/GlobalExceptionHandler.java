package com.blogApp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogApp.payload.Genericresponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Genericresponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		Genericresponse response = new Genericresponse(message, false);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UsernamePasswordNotFoundException.class)
	public ResponseEntity<Genericresponse> usernamePasswordNotFoundException(UsernamePasswordNotFoundException ex){
		
		String message = ex.getMessage();
		Genericresponse response = new Genericresponse(message, false);
		return new ResponseEntity<Genericresponse>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgsNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			response.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}
	/**** 2nd solution ****/

//	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
//	public Map<String, String> handlerMethodArgsNotValidException(MethodArgumentNotValidException ex){
//		Map<String, String> response = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error)->{
//			String fieldName=((FieldError)error).getField();
//			String message = error.getDefaultMessage();
//			response.put(fieldName, message);
//		});
//		return response;
//	}
}
