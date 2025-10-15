package org.restapi.springrestapi.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionErrorCode implements ErrorCode {
	PERMISSION_DENIED(403, "permission_denied");

	private final int status;
	private final String message;
}
