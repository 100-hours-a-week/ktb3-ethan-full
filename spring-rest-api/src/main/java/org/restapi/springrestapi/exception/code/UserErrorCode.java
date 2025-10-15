package org.restapi.springrestapi.exception.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
	// EMAIL_FORMAT_INVALID(400, "email_format_invalid"),
	EMAIL_CONFLICT(HttpStatus.CONFLICT.value(), "email_conflict"),
	// PASSWORD_LENGTH_INVALID(400, "password_length_invalid"),
	// PASSWORD_FORMAT_INVALID(400, "password_format_invalid"),
	NOT_MATCH_NEW_PASSWORD(HttpStatus.BAD_REQUEST.value(), "not_match_new_passwords"),
	NICKNAME_CONFLICT(HttpStatus.CONFLICT.value(), "nickname_conflict"),

	USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "user_not_found")
	;

	private final int status;
	private final String message;
}
