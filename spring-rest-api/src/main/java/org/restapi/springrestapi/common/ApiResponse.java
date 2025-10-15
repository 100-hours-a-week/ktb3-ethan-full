package org.restapi.springrestapi.common;

import java.util.Map;

import org.restapi.springrestapi.exception.code.ErrorCode;
import org.restapi.springrestapi.exception.code.SuccessCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
	private String message;
	private T Data;

	public static <T> ApiResponse<T> ok(SuccessCode code, T data) {
		return new ApiResponse<>(code.getMessage(), data);
	}

	public static ApiResponse<Map<String,Object>> error(String message) {
		return new ApiResponse<>(message, Map.of());
	}
}
