package org.restapi.springrestapi.dto.post;

import java.time.LocalDateTime;

import org.restapi.springrestapi.dto.user.SimpleUserInfo;
import org.restapi.springrestapi.model.Post;

import lombok.Builder;


@Builder(toBuilder = true)
public record PostSimpleResult(
	Long id,
	Long userId,
    String authorNickname,
	String title,
	int likeCount,
	int commentCount,
	int viewCount,
	LocalDateTime createAt
) {
    public static PostSimpleResult from(Post post) {
        return base(post).build();
    }

    public static PostSimpleResult from(PostSimpleResult post, SimpleUserInfo userInfo) {
        return post.toBuilder().authorNickname(userInfo.nickname()).build();
    }

    private static PostSimpleResultBuilder base(Post post) {
        return PostSimpleResult.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .likeCount(post.getLikeUsers().size())
                .commentCount(post.getComments().size())
                .viewCount(post.getViewCount())
                .createAt(post.getCreatedAt());
    }

}
