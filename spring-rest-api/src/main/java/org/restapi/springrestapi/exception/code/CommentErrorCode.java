package org.restapi.springrestapi.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements ErrorCode {
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "comment_not_found"),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "comment_forbidden")
	;

	private final int status;
	private final String message;
}
