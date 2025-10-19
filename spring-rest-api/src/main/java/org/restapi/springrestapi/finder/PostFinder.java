package org.restapi.springrestapi.finder;

import java.util.List;

import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.model.Post;

public interface PostFinder {
	Post findById(Long id);
	List<PostSimpleResult> findAll(int cursor, int limit);
	boolean existsById(Long id);
	/**
	 * 주어진 게시글이 특정 사용자 소유인지 여부를 확인합니다.
	 */
	boolean existsByIdAndUserId(Long postId, Long userId);
}
