package org.example.profitsoftunit2.exception.handler;

import org.example.profitsoftunit2.exception.EntityException;
import org.example.profitsoftunit2.exception.UnknownApiException;
import org.example.profitsoftunit2.exception.error.ApiError;
import org.example.profitsoftunit2.util.wrapper.HTTPResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnknownApiExceptionHandler {

	@ExceptionHandler({UnknownApiException.class})
	public ResponseEntity<Object> notFoundExceptionResponse(EntityException e) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
		return HTTPResponseUtils.of(apiError, e);
	}
}
