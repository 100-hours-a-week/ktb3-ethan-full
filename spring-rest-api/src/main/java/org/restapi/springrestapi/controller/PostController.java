package org.restapi.springrestapi.controller;

import java.util.List;

import org.restapi.springrestapi.common.ApiResponse;
import org.restapi.springrestapi.dto.post.PatchPostLikeResult;
import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.PatchPostResult;
import org.restapi.springrestapi.dto.post.PostListResult;
import org.restapi.springrestapi.dto.post.PostResult;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;
import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.exception.code.SuccessCode;
import org.restapi.springrestapi.security.AuthContext;
import org.restapi.springrestapi.service.post.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
	private final PostService postService;
	private final AuthContext authContext;


	@PostMapping
	public ResponseEntity<ApiResponse<PostSimpleResult>> registerPost(
		@Valid @RequestBody RegisterPostRequest request
	) {
		final Long userId = authContext.requiredUserId();

		return ResponseEntity.status(SuccessCode.REGISTER_SUCCESS.getStatus())
			.body(ApiResponse.ok(SuccessCode.REGISTER_SUCCESS, postService.registerPost(userId, request)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<PostListResult>> getPostList(
		@RequestParam(defaultValue = "0") int cursor,
		@RequestParam(defaultValue = "10") int limit
	) {
		return ResponseEntity.ok()
			.body(ApiResponse.ok(SuccessCode.GET_SUCCESS, postService.getPostList(cursor, limit)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PostResult>> getPostDetail(
		@PathVariable Long id
	) {
		final Long userId = authContext.currentUserIdOrNull();
		return ResponseEntity.ok()
			.body(ApiResponse.ok(SuccessCode.GET_SUCCESS, postService.getPost(userId, id)));
	}

	@PostMapping("/{id}")
	public ResponseEntity<ApiResponse<PostResult>> patchPost(
		@PathVariable Long id,
		@Valid PatchPostRequest request
	) {
		final Long userId = authContext.requiredUserId();
		return ResponseEntity.ok()
			.body(ApiResponse.ok(SuccessCode.PATCH_SUCCESS, postService.updatePost(userId, id, request)));
	}

	@PatchMapping("/{id}/like")
	public ResponseEntity<ApiResponse<PatchPostLikeResult>> updatePostLike(
		@PathVariable Long id
	) {
		final Long userId = authContext.requiredUserId();
		return ResponseEntity.ok()
			.body(ApiResponse.ok(SuccessCode.PATCH_SUCCESS, postService.updatePostLike(userId, id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deletePost(
		@PathVariable Long id
	) {
		final Long userId = authContext.requiredUserId();
		postService.deletePost(userId, id);
		return ResponseEntity.noContent().build();
	}


}
