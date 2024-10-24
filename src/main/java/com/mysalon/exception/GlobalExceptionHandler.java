package com.mysalon.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Exception handler to handle any general exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}

	// To handle validations like @NotBlank which are on fields of entity class. //@Valid should be added in Customer class along with Mapping
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return errors;
	}
	
	@ExceptionHandler(DuplicateCustomerException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleDuplicateCustomerException(DuplicateCustomerException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleBadRequestException(BadRequestException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}

	@ExceptionHandler(NoCustomerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNoCustomerFoundException(NoCustomerFoundException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}

	@ExceptionHandler(NoPaymentFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNoPaymentFoundException(NoPaymentFoundException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}
	
	@ExceptionHandler(NoCardFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNoCardFoundException(NoCardFoundException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), "/card");
		return res;
	}
	
	@ExceptionHandler(NoAppointmentFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNoAppointmentFoundException(NoAppointmentFoundException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), "/appointment");
		return res;
	}

	@ExceptionHandler(DuplicateAppointmentException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleDuplicateAppointmentException(DuplicateAppointmentException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}
	
	@ExceptionHandler(DuplicateServiceException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse handleDuplicateServiceException(DuplicateServiceException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInsufficientBalanceException(InsufficientBalanceException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}
	
	@ExceptionHandler(NoReceiptFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNoReceiptFoundException(NoReceiptFoundException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), "/appointment");
		return res;
	}
	
	@ExceptionHandler(NoOrderFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNoOrderFoundException(NoOrderFoundException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), "/appointment");
		return res;
	}
}
