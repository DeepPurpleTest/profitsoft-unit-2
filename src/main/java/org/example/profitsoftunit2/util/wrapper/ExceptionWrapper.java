package org.example.profitsoftunit2.util.wrapper;

import org.example.profitsoftunit2.exception.EntityException;
import org.example.profitsoftunit2.exception.error.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionWrapper {

	public ResponseEntity<Object> of(ApiError apiError,
									 EntityException e, BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		bindingResult.getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		apiError.setMessage(e.getMessage());
		apiError.setErrors(errors);
		apiError.setStackTrace(Arrays.toString(e.getStackTrace()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	public ResponseEntity<Object> of(ApiError apiError,
									 EntityException e) {
		apiError.setMessage(e.getMessage());
		apiError.setStackTrace(Arrays.toString(e.getStackTrace()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
