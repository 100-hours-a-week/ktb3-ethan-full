package org.restapi.springrestapi.model;

import java.time.LocalDateTime;
import java.util.List;

import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;

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

	private List<Long> likeUsers;
	private List<Long> comments;
	private int viewCount;

	LocalDateTime createdAt;

	public static Post from(Long userId, RegisterPostRequest command) {
		return Post.builder()
			.userId(userId)
			.title(command.title())
			.content(command.content())
			.image(command.image())
			.createdAt(LocalDateTime.now())
			.build();
	}
	public static Post from(PatchPostRequest command, Post prevPost) {
		return prevPost.toBuilder()
			.title(command.title())
			.content(command.content())
			.image(command.image())
			.build();
	}
}
