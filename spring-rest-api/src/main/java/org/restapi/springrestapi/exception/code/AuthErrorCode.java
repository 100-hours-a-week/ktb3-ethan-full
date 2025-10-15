package org.restapi.springrestapi.exception.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
	AUTH_REQUIRED(HttpStatus.UNAUTHORIZED.value(), "auth_required"),
	INVALID_EMAIL_OR_PASSWORD(HttpStatus.UNAUTHORIZED.value(), "invalid_email_or_password"),
	// INVALID_OR_REUSED_REFRESH_TOKEN(401, "invalid_or_reused_refresh_token")
	;

	private final int status;
	private final String message;
}
