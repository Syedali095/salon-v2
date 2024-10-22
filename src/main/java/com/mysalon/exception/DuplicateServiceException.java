package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateServiceException extends RuntimeException{

	private static final long serialVersionUID = 11L;

	public DuplicateServiceException(String message) {
		super(message);
	}
	
}
