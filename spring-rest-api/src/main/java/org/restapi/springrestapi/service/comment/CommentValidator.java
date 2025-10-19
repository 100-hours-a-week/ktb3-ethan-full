package org.restapi.springrestapi.service.comment;

public interface CommentValidator {
    void validateUser(Long userId);
    void validatePost(Long postId);
    void validateComment(Long commentId);
    default void validateOrigin(Long userId, Long postId) {
        validateUser(userId);
        validatePost(postId);
    }
}