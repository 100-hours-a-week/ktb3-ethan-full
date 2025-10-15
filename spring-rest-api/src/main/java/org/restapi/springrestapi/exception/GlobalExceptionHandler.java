package org.restapi.springrestapi.exception;

import java.util.Map;

import org.restapi.springrestapi.exception.code.ErrorCode;
import org.restapi.springrestapi.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, Object>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.error(
				e.getBindingResult()
					.getAllErrors()
					.get(0)
					.getDefaultMessage()
			));
	}

	@ExceptionHandler(AppException.class)
	public ResponseEntity<ApiResponse<Map<String, Object>>> handleAppException(AppException e) {
		final ErrorCode code = e.getErrorCode();
		return ResponseEntity.status(code.getStatus())
			.body(ApiResponse.error(code.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Map<String, Object>>> handleAnyException(Exception e) {
		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.error("internal_server_error"));
	}
}