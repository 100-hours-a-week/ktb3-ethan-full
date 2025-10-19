package org.restapi.springrestapi.service.post;

import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.PostErrorCode;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.finder.UserFinder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostValidatorImpl implements PostValidator {
    private final UserFinder userFinder;
    private final PostFinder postFinder;

    @Override
    public void validateAuthorId(Long userId) {
        if (!userFinder.existsById(userId)) {
            throw new AppException(UserErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    public void validatePostExists(Long postId) {
        if (!postFinder.existsById(postId)) {
            throw new AppException(PostErrorCode.POST_NOT_FOUND);
        }
    }

    @Override
    public void validateAuthorPermission(Long userId, Long postId) {
        if (!postFinder.existsByIdAndUserId(postId, userId)) {
            throw new AppException(PostErrorCode.PERMISSION_DENIED);
        }
    }
}