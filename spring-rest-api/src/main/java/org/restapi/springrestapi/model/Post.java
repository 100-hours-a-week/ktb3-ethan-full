package org.restapi.springrestapi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Post {
	private final Long id;
	private final Long userId;
	private String title;
	private String content;
	private String image; // nullable

	private List<Long> likeUsers = new ArrayList<>();
	private List<Long> comments  = new ArrayList<>();
	private int viewCount;

	LocalDateTime createdAt;

	public static Post from(Long userId, RegisterPostRequest command) {
		return Post.builder()
			.userId(userId)
			.title(command.getTitle())
			.content(command.getContent())
			.image(command.getPostImage())
			.build();
	}
	public static Post from(PatchPostRequest command, Post prevPost) {
		return prevPost.toBuilder()
			.title(command.title())
			.content(command.content())
			.image(command.image())
			.build();
	}

	@PostConstruct
	public void init() {
		this.createdAt = LocalDateTime.now();
	}
}
