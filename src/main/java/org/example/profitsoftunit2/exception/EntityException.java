package org.example.profitsoftunit2.exception;

/**
 * Abstract class for all custom exceptions with field causedBy
 * causedBy used if the exception was raised in response to a caught exception
 */
public abstract class EntityException extends RuntimeException {

	protected EntityException(String message) {
		super(message);
	}
}
