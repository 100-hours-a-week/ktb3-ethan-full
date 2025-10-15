package org.restapi.springrestapi.dto.post;

import java.time.LocalDateTime;
import java.util.List;

import org.restapi.springrestapi.model.Post;

import lombok.Builder;

@Builder
public class PostResult {
	private Long id;
	private Long userId;
	private String title;
	private String content;
	private boolean didLike;
	private int likeCount;
	private int commentCount;
	private int viewCount;
	private LocalDateTime createdAt;
	private List<Long> commentIds;
	private String image;

	public static PostResult from(Post post) {
		return PostResult.builder()
			.id(post.getId())
			.userId(post.getUserId())
			.title(post.getTitle())
			.content(post.getContent())
			.likeCount(post.getLikeUsers().size())
			.commentCount(post.getComments().size())
			.viewCount(post.getViewCount())
			.createdAt(post.getCreatedAt())
			.commentIds(post.getComments())
			.image(post.getImage())
			.build();
	}

	public void markLike() {
		this.didLike = true;
	}

}
