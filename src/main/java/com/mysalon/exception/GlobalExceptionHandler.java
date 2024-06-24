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
	
	//Exception handler to handle any general exception (without using @ResponseStatus)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception e){
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}
	
	//Exception handler to handle "DuplicateCustomerException" exception  //Real time way of writing
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
	
	//We can also create an Error response class to print all the errors in a same pattern
	//This is also a Real time way of writing.
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleBadRequestException(BadRequestException ex) {
		ErrorResponse res = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), "/customer");
		return res;
	}
	//In the above format, using @ResponseStatus(HttpStatus.CONFLICT) is important in BadRequestException Class and handleBadRequestException method in GlobalExceptionHandler Class
	//If we are not using @ResponseStatus then we should write the complete format which we need to print the exception.
	/*Suppose we are using ErrorResponse Class approach which prints all the exceptions in same pattern but we want some exceptions to be printed in custom way, 
	 * like if we want to print only message and status then we need to follow below approach */ 
	
	//Exception handler to handle "NoCustomerFoundException" exception (without @ResponseStatus)
	@ExceptionHandler(NoCustomerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoCustomerFoundException(NoCustomerFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()); //In ErrorResponse class there should be a contructor of only these 2 arguments
		ResponseEntity<ErrorResponse> customResponse = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		return customResponse;
	}
	
	//Exception handler to handle "MethodArgumentNotValidException" exception //To handle @NotBlank
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timeStamp", LocalDateTime.now());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Validation error");
		
		Map<String, String> errors = new HashMap<>();
		for(FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		body.put("message", errors);
		body.put("path", "/save/customer");
		ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
}
