package org.restapi.springrestapi.legacy.repository;

import java.util.List;
import java.util.Optional;

import org.restapi.springrestapi.model.Comment;

public interface CommentRepository {
	Comment save(Comment comment);
	Optional<Comment> findById(Long id);
	List<Comment> findAll(Long postId, int cursor, int limit);
	void deleteById(Long id);
}
