package org.example.profitsoftunit2.util.wrapper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.example.profitsoftunit2.exception.AppException;
import org.example.profitsoftunit2.exception.error.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for build responses while exception is handled
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HTTPResponseUtils {

	/**
	 * Method for building response with binding result
	 */
	public static ResponseEntity<Object> of(ApiError apiError,
											AppException e, BindingResult bindingResult) {
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

	public static ResponseEntity<Object> of(ApiError apiError,
									 AppException e) {
		apiError.setMessage(e.getMessage());
		apiError.setStackTrace(Arrays.toString(e.getStackTrace()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
