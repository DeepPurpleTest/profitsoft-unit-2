package org.example.profitsoftunit2.exception;

import lombok.Getter;

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
