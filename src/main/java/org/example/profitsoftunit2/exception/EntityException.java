package org.example.profitsoftunit2.exception;

/**
 * Abstract class for all custom exceptions
 */
public abstract class EntityException extends RuntimeException {

	protected EntityException(String message) {
		super(message);
	}
}
