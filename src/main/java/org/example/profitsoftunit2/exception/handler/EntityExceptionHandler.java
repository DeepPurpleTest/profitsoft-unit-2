package org.example.profitsoftunit2.exception.handler;

import org.example.profitsoftunit2.exception.EntityNotFoundException;
import org.example.profitsoftunit2.exception.EntityValidationException;
import org.example.profitsoftunit2.exception.error.ApiError;
import org.example.profitsoftunit2.util.wrapper.HTTPResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Custom exception handler
 */
@RestControllerAdvice
public class EntityExceptionHandler {

	/**
	 * Handle custom EntityValidation exception
	 * If exception have binding result then generate more detailed response with the fields and their errors
	 */
	@ExceptionHandler({EntityValidationException.class})
	public ResponseEntity<Object> handle(EntityValidationException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		if (e.getBindingResult() != null) {
			return HTTPResponseUtils.of(apiError, e, e.getBindingResult());
		}

		return HTTPResponseUtils.of(apiError, e);
	}

	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<Object> handle(EntityNotFoundException e) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		return HTTPResponseUtils.of(apiError, e);
	}
}
