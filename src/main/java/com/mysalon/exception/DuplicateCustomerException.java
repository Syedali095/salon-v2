package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCustomerException extends RuntimeException{
	private static final long serialVersionUID = 2L;
	
	public DuplicateCustomerException() {
		super("Customer already exists");
	}
}
