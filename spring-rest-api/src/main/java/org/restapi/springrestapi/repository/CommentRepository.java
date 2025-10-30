package org.restapi.springrestapi.repository;

import org.restapi.springrestapi.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByIdAndUserId(Long id, Long userId);

    @Query(
            """
            select c
            from Comment c
            where c.post.id = :postId and (:cursorId is null or c.id < :cursorId)
            """
    )
    Slice<Comment> findSlice(@Param("postId") Long postId, @Param("cursorId") Long cursorId, Pageable pageable);
}
