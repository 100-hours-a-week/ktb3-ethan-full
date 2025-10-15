package org.restapi.springrestapi.dto.post;

import org.restapi.springrestapi.model.Post;

import lombok.Builder;

@Builder
public record PatchPostLikeResult(
	int likeCount,
	boolean didLike
) {
	public static PatchPostLikeResult from(Post post, boolean didLike) {
		return PatchPostLikeResult.builder()
			.likeCount(post.getLikeUsers().size())
			.didLike(didLike)
			.build();
	}
}
