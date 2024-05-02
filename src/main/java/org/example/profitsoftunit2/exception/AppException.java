package org.example.profitsoftunit2.exception;

/**
 * Abstract class for all custom exceptions
 */
public abstract class AppException extends RuntimeException {

	protected AppException(String message) {
		super(message);
	}
}
