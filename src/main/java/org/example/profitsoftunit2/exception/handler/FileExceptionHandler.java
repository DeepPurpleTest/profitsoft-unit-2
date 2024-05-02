package org.example.profitsoftunit2.exception.handler;

import org.example.profitsoftunit2.exception.FileValidationException;
import org.example.profitsoftunit2.exception.error.ApiError;
import org.example.profitsoftunit2.util.wrapper.HTTPResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionHandler {

	@ExceptionHandler({FileValidationException.class})
	public ResponseEntity<Object> handle(FileValidationException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		return HTTPResponseUtils.of(apiError, e);
	}
}
