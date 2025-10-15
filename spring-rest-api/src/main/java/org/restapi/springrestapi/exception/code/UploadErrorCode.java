package org.restapi.springrestapi.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadErrorCode implements ErrorCode {
	FILE_SIZE_OVER(400, "file_size_over"),
	INVALID_FILE_TYPE(400, "invalid_file_type");

	private final int status;
	private final String message;
}
