package org.restapi.springrestapi.controller;

import org.restapi.springrestapi.common.ApiResponse;
import org.restapi.springrestapi.dto.comment.CommentListResult;
import org.restapi.springrestapi.dto.comment.CommentResult;
import org.restapi.springrestapi.dto.comment.PatchCommentRequest;
import org.restapi.springrestapi.dto.comment.RegisterCommentRequest;
import org.restapi.springrestapi.exception.code.SuccessCode;
import org.restapi.springrestapi.security.AuthContext;
import org.restapi.springrestapi.service.comment.CommentService;
import org.springframework.http.HttpStatus;
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
public class CommentController {
	private final CommentService commentService;
	private final AuthContext authContext;

	@PostMapping("/{postId}/comments")
	public ResponseEntity<ApiResponse<CommentResult>> createComment(
		@PathVariable Long postId,
		@RequestBody RegisterCommentRequest request
	) {
		final Long userId = authContext.requiredUserId();
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.ok(SuccessCode.REGISTER_SUCCESS, commentService.registerComment(userId, request, postId)));
	}

	@GetMapping("/{postId}/comments")
	public ResponseEntity<ApiResponse<CommentListResult>> getCommentAll(
		@PathVariable Long postId,
		@RequestParam(defaultValue = "0") int cursor,
		@RequestParam(defaultValue = "10") int limit
	) {
		return ResponseEntity.ok()
			.body(ApiResponse.ok(SuccessCode.GET_SUCCESS, commentService.getCommentList(postId, cursor, limit)));
	}

	@PatchMapping("/{postId}/comments/{id}")
	public ResponseEntity<ApiResponse<CommentResult>> patchComment(
		@PathVariable Long postId,
		@PathVariable Long id,
		@Valid PatchCommentRequest request
	) {
		final Long userId = authContext.requiredUserId();
		return ResponseEntity.ok()
			.body(ApiResponse.ok(SuccessCode.PATCH_SUCCESS, commentService.updateComment(userId, request, postId, id)));
	}

	@DeleteMapping("/{postId}/comments/{id}")
	public ResponseEntity<Void> deleteComment(
		@PathVariable Long postId,
		@PathVariable Long id
	) {
		final Long userId = authContext.requiredUserId();
		commentService.deleteComment(userId, id, postId);
		return ResponseEntity.noContent().build();
	}
}
