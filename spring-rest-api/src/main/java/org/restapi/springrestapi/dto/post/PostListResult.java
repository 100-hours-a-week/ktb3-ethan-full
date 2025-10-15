package org.restapi.springrestapi.dto.post;

import java.util.List;

import lombok.Builder;

@Builder
public record PostListResult(
	List<PostSimpleResult> posts,
	int nextCursor
) {
	public static PostListResult from(List<PostSimpleResult> posts, int nextCursor) {
		return PostListResult.builder()
			.posts(posts)
			.nextCursor(nextCursor)
			.build();
	}
}
