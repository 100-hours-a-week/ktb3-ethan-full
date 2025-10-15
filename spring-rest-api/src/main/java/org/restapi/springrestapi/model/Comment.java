package org.restapi.springrestapi.model;

import java.time.LocalDateTime;

import org.restapi.springrestapi.dto.comment.RegisterCommentRequest;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Comment {
	private final Long id;
	private final Long userId;
	private final Long postId;
	private String content;
	private LocalDateTime createdAt;

	public static Comment from(RegisterCommentRequest command, Long userId, Long postId) {
		return Comment.builder()
			.userId(userId)
			.postId(postId)
			.content(command.content())
			.build();
	}

	@PostConstruct
	public void init() {
		this.createdAt = LocalDateTime.now();
	}

	public void updateContent(String newContent) {
		if (newContent != null) {
			this.content = newContent;
		}
	}
}
