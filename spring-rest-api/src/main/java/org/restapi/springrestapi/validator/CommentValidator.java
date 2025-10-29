package org.restapi.springrestapi.validator;

public interface CommentValidator {
    void validateOwner(Long commentId, Long userId);
}