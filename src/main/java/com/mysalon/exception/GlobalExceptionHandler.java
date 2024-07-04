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
public class GlobalExceptionHandler{
	
	//Exception handler to handle any general exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception e){
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}
	
	//HashMap approach
	@ExceptionHandler(DuplicateCustomerException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateCustomerException(DuplicateCustomerException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timeStamp", LocalDateTime.now());
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", HttpStatus.CONFLICT.getReasonPhrase());
		body.put("message", ex.getMessage());
		body.put("path", "/customer");
		ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(body, HttpStatus.CONFLICT);
		return responseEntity;
	}
	
	//ErrorResponse approach
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleBadRequestException(BadRequestException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}
	
	//Mixed approach 
	@ExceptionHandler(NoCustomerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoCustomerFoundException(NoCustomerFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		ResponseEntity<ErrorResponse> customResponse = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		return customResponse;
	}
	
	//To handle validations like @NotBlank which are on fields of entity class.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timeStamp", LocalDateTime.now());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Validation error");
		body.put("path", "/save/customer");
		
		Map<String, String> errors = new HashMap<>();
		for(FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		body.put("message", errors);
		
		ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
}
