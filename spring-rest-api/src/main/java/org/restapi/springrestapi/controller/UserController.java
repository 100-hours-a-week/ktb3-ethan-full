package org.restapi.springrestapi.controller;

import org.restapi.springrestapi.dto.user.ChangePasswordRequest;
import org.restapi.springrestapi.dto.user.PatchProfileRequest;
import org.restapi.springrestapi.dto.user.PatchProfileResponse;
import org.restapi.springrestapi.dto.user.RegisterUserRequest;
import org.restapi.springrestapi.dto.user.RegisterUserResponse;
import org.restapi.springrestapi.dto.user.UserProfileResult;
import org.restapi.springrestapi.exception.code.SuccessCode;
import org.restapi.springrestapi.security.AuthContext;
import org.restapi.springrestapi.service.user.UserService;
import org.restapi.springrestapi.common.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final AuthContext authContext;

	@PostMapping
	public ResponseEntity<APIResponse<RegisterUserResponse>> register(
		@Valid @RequestBody RegisterUserRequest request
	) {
		final Long id = userService.register(request);
		return ResponseEntity.status(SuccessCode.REGISTER_SUCCESS.getStatus())
				.body(APIResponse.ok(SuccessCode.REGISTER_SUCCESS, new RegisterUserResponse(id)));
		/*
		ApiResponse가 ResponseEntity을 생성하고 응답해도 되나?
		컨트롤러에서 상태코드를 중복해서 지정하는게 최선인가?
		 */
	}
	@GetMapping("/{id}")
	public ResponseEntity<APIResponse<UserProfileResult>> getUserProfile(
		@PathVariable Long id
	) {
		return ResponseEntity.ok()
				.body(APIResponse.ok(SuccessCode.GET_SUCCESS, userService.getUserProfile(id)));
	}

	@PatchMapping
	public ResponseEntity<APIResponse<PatchProfileResponse>> updateProfile(
		@Valid @RequestBody PatchProfileRequest request
	) {
		final Long id = authContext.requiredUserId();
		userService.updateProfile(id, request);
		return ResponseEntity.ok(APIResponse.ok(
			SuccessCode.PATCH_SUCCESS, PatchProfileResponse.from(request)));
	}

	@PatchMapping("/password")
	public ResponseEntity<APIResponse<Void>> changePassword(
		@Valid @RequestBody ChangePasswordRequest request
	) {
		final Long id = authContext.requiredUserId();
		userService.changePassword(id, request);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteUser() {
		final Long id = authContext.requiredUserId();
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
