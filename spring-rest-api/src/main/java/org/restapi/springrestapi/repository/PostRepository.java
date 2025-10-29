package org.restapi.springrestapi.repository;

import org.restapi.springrestapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(
        """
        select p.id
        from Post p
        order by p.id desc
        """
    )
    List<Long> findLatestIds(Pageable pageable);


    boolean existsByIdAndAuthorId(Long id, Long authorId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Post p set p.commentCount = p.commentCount + 1 where p.id = :id")
    int increaseCommentCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Post p set p.commentCount = p.commentCount - 1 where p.id = :id")
    int decreaseCommentCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Post p set p.likeCount = p.likeCount + 1 where p.id = :id")
    int increaseLikeCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Post p set p.likeCount = p.likeCount - 1 where p.id = :id")
    int decreaseLikeCount(@Param("id") Long id);
}
