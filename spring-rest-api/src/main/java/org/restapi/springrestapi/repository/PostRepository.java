package org.restapi.springrestapi.repository;

import java.util.List;
import java.util.Optional;

import org.restapi.springrestapi.model.Post;

public interface PostRepository {
	Post save(Post post);
	Optional<Post> findById(Long id);
	List<Post> findAll(int cursor, int limit);
	boolean existsByIdAndUserId(Long id, Long userId);
	void deleteById(Long id);
}
