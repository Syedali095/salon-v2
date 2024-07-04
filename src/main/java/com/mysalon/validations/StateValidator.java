package com.mysalon.validations;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidator implements ConstraintValidator<ValidateState, String>{

	@Override
	public boolean isValid(String state, ConstraintValidatorContext context) {
		List<String> states = Arrays.asList("TS", "MH", "AP");
		Boolean result = states.contains(state);
		return result;
	}

}
