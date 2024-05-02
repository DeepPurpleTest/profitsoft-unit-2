package org.example.profitsoftunit2.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Custom api response while get errors
 */
@Getter
@Setter
public class ApiError {

	private String message;

	/**
	 * Sub errors like many fields validation
	 * k - field, v - message with error
	 */
	private Map<String, String> errors;

	private String stackTrace;

	private HttpStatus status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
}
