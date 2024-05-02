package org.example.profitsoftunit2.exception;

import lombok.Getter;

/**
 * Abstract class for all custom exceptions with field causedBy
 * causedBy used if the exception was raised in response to a caught exception
 */
@Getter
public abstract class EntityException extends RuntimeException {
	protected Exception causedBy;

	protected EntityException(String message) {
		super(message);
	}

	protected EntityException(String message, Exception causedBy) {
		super(message);
		this.causedBy = causedBy;
	}
}
