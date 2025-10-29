package org.restapi.springrestapi.finder;

import java.util.List;

import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.model.Post;

public interface PostFinder {
	Post findById(Long id);
    Post findProxyById(Long id);
	List<PostSimpleResult> findAll(int cursor, int limit);
	boolean existsById(Long id);
	boolean existsByIdAndAuthorId(Long postId, Long authorId);
}
