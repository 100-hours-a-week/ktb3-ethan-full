package org.restapi.springrestapi.exception;

import java.util.Map;

import org.restapi.springrestapi.exception.code.ErrorCode;
import org.restapi.springrestapi.common.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<APIResponse<Map<String, Object>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.warn("Validation failed: {}", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(APIResponse.error(
				e.getBindingResult()
					.getAllErrors()
					.get(0)
					.getDefaultMessage()
			));
	}

	@ExceptionHandler(AppException.class)
	public ResponseEntity<APIResponse<Map<String, Object>>> handleAppException(AppException e) {
		final ErrorCode code = e.getErrorCode();
		log.error("AppException: status={}, code={}, message={}", code.getStatus(), String.valueOf(code), code.getMessage(), e);
		return ResponseEntity.status(code.getStatus())
			.body(APIResponse.error(code.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIResponse<Map<String, Object>>> handleAnyException(Exception e) {
		log.error("Unhandled exception", e);
		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(APIResponse.error("internal_server_error"));
	}
}