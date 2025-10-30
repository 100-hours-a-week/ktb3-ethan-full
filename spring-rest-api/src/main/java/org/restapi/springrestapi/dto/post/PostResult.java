package org.restapi.springrestapi.dto.post;

import java.time.LocalDateTime;
import java.util.List;

import org.restapi.springrestapi.model.Comment;
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
	private List<Comment> comments;
	private String image;

	public static PostResult from(Post post, boolean didLike) {
		return PostResult.builder()
			.id(post.getId())
			.userId(post.getAuthor().getId())
			.title(post.getTitle())
			.content(post.getContent())
			.likeCount(post.getLikeCount())
			.commentCount(post.getCommentCount())
			.viewCount(post.getViewCount())
			.createdAt(post.getCreatedAt())
			.comments(post.getComments())
			.image(post.getThumbnailImageUrl())
                .didLike(didLike)
			.build();
	}
    public static PostResult from(Post post) {
        return PostResult.from(post, false);
    }
}
