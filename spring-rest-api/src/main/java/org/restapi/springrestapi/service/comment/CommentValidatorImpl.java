package org.restapi.springrestapi.service.comment;

import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.CommentErrorCode;
import org.restapi.springrestapi.exception.code.PostErrorCode;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.finder.CommentFinder;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.finder.UserFinder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentValidatorImpl implements CommentValidator {
    private final UserFinder userFinder;
    private final PostFinder postFinder;
    private final CommentFinder commentFinder;

    @Override
    public void validateUser(Long userId) {
        if (!userFinder.existsById(userId)) {
            throw new AppException(UserErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    public void validatePost(Long postId) {
        if (!postFinder.existsById(postId)) {
            throw new AppException(PostErrorCode.POST_NOT_FOUND);
        }
    }

    @Override
    public void validateComment(Long commentId) {
        if (!commentFinder.existsById(commentId)) {
            throw new AppException(CommentErrorCode.COMMENT_NOT_FOUND);
        }
    }
}