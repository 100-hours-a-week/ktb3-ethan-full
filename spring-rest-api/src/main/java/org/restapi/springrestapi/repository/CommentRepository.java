package org.restapi.springrestapi.repository;

import org.restapi.springrestapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
