package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoReceiptFoundException extends RuntimeException{

	private static final long serialVersionUID = 12L;

	public NoReceiptFoundException(String message) {
		super(message);
	}
	
}
