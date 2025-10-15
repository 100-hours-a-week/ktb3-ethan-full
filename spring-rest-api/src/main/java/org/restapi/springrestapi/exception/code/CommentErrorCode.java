package org.restapi.springrestapi.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements ErrorCode{
	COMMENT_NOT_FOUND(404, "comment_not_found")
	;

	private final int status;
	private final String message;
}
