package org.restapi.springrestapi.dto.post;

import java.time.LocalDateTime;

import org.restapi.springrestapi.model.Post;

import lombok.Builder;


@Builder
public record PostSimpleResult(
	Long id,
	Long userId,
	String title,
	int likeCount,
	int commentCount,
	int viewCount,
	LocalDateTime createAt
) {
	public static PostSimpleResult from(Post post) {
		return PostSimpleResult.builder()
			.id(post.getId())
			.userId(post.getUserId())
			.title(post.getTitle())
			.likeCount(post.getLikeUsers().size())
			.commentCount(post.getComments().size())
			.viewCount(post.getViewCount())
			.build();
	}
}
