package org.restapi.springrestapi.dto.comment;

import java.time.LocalDateTime;

import org.restapi.springrestapi.model.Comment;

import lombok.Builder;

@Builder
public record CommentResult(
	Long id,
	LocalDateTime createAt,
	Long userId,
	String content
) {
	public static CommentResult from(Comment comment) {
		return CommentResult.builder()
			.id(comment.getId())
			.createAt(comment.getCreatedAt())
			.userId(comment.getUserId())
			.content(comment.getContent())
			.build();
	}
}
