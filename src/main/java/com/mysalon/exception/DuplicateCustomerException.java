package com.mysalon.exception;

public class DuplicateCustomerException extends RuntimeException{
	private static final long serialVersionUID = 2L;
	
	public DuplicateCustomerException() {
		super("Customer already exists");
	}
}
