package com.ylog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<?> handleFeignClientException(FeignException e) {

		return new ResponseEntity<>(String.valueOf(e.contentUTF8()), HttpStatus.valueOf(e.status()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		Map<String, String> errorMap = new HashMap<String, String>();
		e.getBindingResult().getAllErrors().forEach(error -> {

			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();

			errorMap.put(fieldName, message);

		});

		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
	}

}
