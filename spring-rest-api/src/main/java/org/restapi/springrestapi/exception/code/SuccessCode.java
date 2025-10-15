package org.restapi.springrestapi.exception.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
	GET_SUCCESS(HttpStatus.OK.value(), "resource_get"),
	PATCH_SUCCESS(HttpStatus.OK.value(), "resource_patch"),
	REGISTER_SUCCESS(HttpStatus.CREATED.value(), "resource_created"),

	LOGIN_SUCCESS(HttpStatus.OK.value(), "login_success")
	;

	private final int status;
	private final String message;
}
