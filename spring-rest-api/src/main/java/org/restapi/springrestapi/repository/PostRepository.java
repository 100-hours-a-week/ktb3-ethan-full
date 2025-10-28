package org.restapi.springrestapi.repository;

import org.restapi.springrestapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
