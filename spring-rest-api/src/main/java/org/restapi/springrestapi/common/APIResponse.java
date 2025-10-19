package org.restapi.springrestapi.common;

import java.util.Map;

import org.restapi.springrestapi.exception.code.SuccessCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class APIResponse<T> {
	private String message;
	private T Data;

	public static <T> APIResponse<T> ok(SuccessCode code, T data) {
		return new APIResponse<>(code.getMessage(), data);
	}

	public static APIResponse<Map<String,Object>> error(String message) {
		return new APIResponse<>(message, Map.of());
	}
}
