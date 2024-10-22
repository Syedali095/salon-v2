package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientBalanceException extends RuntimeException{

	private static final long serialVersionUID = 10L;

	public InsufficientBalanceException(String message) {
		super(message);
	}
}
