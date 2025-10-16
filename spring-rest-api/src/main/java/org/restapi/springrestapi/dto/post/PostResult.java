package org.restapi.springrestapi.dto.post;

import java.time.LocalDateTime;
import java.util.List;

import org.restapi.springrestapi.model.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
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
			.likeCount(post.getLikeUsers() != null ? post.getLikeUsers().size() : 0)
			.commentCount(post.getComments() != null ? post.getComments().size() : 0)
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
