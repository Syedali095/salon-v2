package com.mysalon.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StateValidator.class)
public @interface ValidateState {
	public String message() default "Enter the among MH, TS, AP";
	
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};

}
