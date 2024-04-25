package org.example.profitsoftunit2.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class EntityValidationException extends EntityException {
	private final transient BindingResult bindingResult;

	public EntityValidationException(String message, BindingResult bindingResult) {
		super(message);
		this.bindingResult = bindingResult;
	}
}
