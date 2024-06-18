package com.ylog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<?> handleException(FeignException e) {

		return new ResponseEntity<>(String.valueOf(e.contentUTF8()), HttpStatus.valueOf(e.status()));
	}

}
