package com.mysalon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoAppointmentFoundException extends RuntimeException{
	private static final long serialVersionUID = 6L;

	public NoAppointmentFoundException(String message) {
		super(message);
	}
}
