package org.restapi.springrestapi.service.post;

public interface PostValidator {
    void validateAuthorId(Long userId);
    void validatePostExists(Long postId);
    void validateAuthorPermission(Long userId, Long postId);
    default void validateAuthorInfo(Long userId, Long postId) {
        validateAuthorId(userId);
        validatePostExists(postId);
        validateAuthorPermission(userId, postId);
    }
}