package org.restapi.springrestapi.controller;

import org.restapi.springrestapi.dto.auth.LoginResult;
import org.restapi.springrestapi.dto.user.LoginRequest;
import org.restapi.springrestapi.exception.code.SuccessCode;
import org.restapi.springrestapi.service.auth.AuthService;
import org.restapi.springrestapi.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/token")
	public ResponseEntity<ApiResponse<LoginResult>> login(
		@RequestBody LoginRequest request
	) {
		return ResponseEntity.ok(ApiResponse.ok(SuccessCode.LOGIN_SUCCESS, authService.login(request)));
	}

}
