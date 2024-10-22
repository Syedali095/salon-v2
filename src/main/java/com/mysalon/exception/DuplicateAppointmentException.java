package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateAppointmentException extends RuntimeException {

	private static final long serialVersionUID = 9L;

	public DuplicateAppointmentException(String message) {
		super(message);
	}
	
}
