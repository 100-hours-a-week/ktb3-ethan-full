package org.restapi.springrestapi.finder;

import java.util.List;

import org.restapi.springrestapi.dto.comment.CommentResult;
import org.restapi.springrestapi.model.Comment;

public interface CommentFinder {
	Comment findById(Long id);
	List<CommentResult> findAll(Long postId, int cursor, int limit);
	boolean existsById(Long id);
}
