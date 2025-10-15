package org.restapi.springrestapi.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
	INVALID_REQUEST(400, "invalid_request"),
	NOT_FOUND(404, "not_found"),
	INTERNAL(500, "internal_server_error");

	private final int status;
	private final String message;
}
