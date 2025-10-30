package org.restapi.springrestapi.exception.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements ErrorCode {
	POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "post_not_found"),
	PERMISSION_DENIED(HttpStatus.FORBIDDEN.value(), "permission_denied")
	;

	private final int status;
	private final String message;
}
