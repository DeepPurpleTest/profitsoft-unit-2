package org.example.profitsoftunit2.exception.handler;

import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit2.exception.EntityException;
import org.example.profitsoftunit2.exception.EntityNotFoundException;
import org.example.profitsoftunit2.exception.EntityValidationException;
import org.example.profitsoftunit2.exception.error.ApiError;
import org.example.profitsoftunit2.exception.wrapper.ExceptionWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class EntityExceptionHandler {

	private final ExceptionWrapper exceptionWrapper;
	@ExceptionHandler({EntityValidationException.class})
	public ResponseEntity<Object> validationExceptionResponse(EntityValidationException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		if (e.getBindingResult() != null) {
			return exceptionWrapper.of(apiError, e, e.getBindingResult());
		}

		return exceptionWrapper.of(apiError, e);
	}

	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<Object> notFoundExceptionResponse(EntityException e) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		return exceptionWrapper.of(apiError, e);
	}
}
