package com.mysalon.exception;

public class NoServiceFoundException extends RuntimeException{
	private static final long serialVersionUID = 4L;
	
	public NoServiceFoundException(String message) {
		super(message);
	}
}
