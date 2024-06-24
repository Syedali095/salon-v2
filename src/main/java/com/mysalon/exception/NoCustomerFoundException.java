package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCustomerFoundException extends RuntimeException {

	public NoCustomerFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
