package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPaymentFoundException extends RuntimeException {
	private static final long serialVersionUID = 8L;

	public NoPaymentFoundException(String message) {
		super(message);
	}
}
