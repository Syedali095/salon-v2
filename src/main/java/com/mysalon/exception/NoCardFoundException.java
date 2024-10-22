package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCardFoundException extends RuntimeException{
	private static final long serialVersionUID = 5L;
	
	public NoCardFoundException(String message) {
		super(message);
	}
}
