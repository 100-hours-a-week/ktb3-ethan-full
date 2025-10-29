package org.restapi.springrestapi.validator;

import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.CommentErrorCode;
import org.restapi.springrestapi.exception.code.PostErrorCode;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.finder.CommentFinder;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.finder.UserFinder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentValidatorImpl implements CommentValidator {
    private final CommentFinder commentFinder;

    @Override
    public void validateOwner(Long commentId, Long userId) {
        if (commentFinder.existsByIdAndUserId(commentId, userId)) {
            throw new AppException(CommentErrorCode.COMMENT_FORBIDDEN);
        }
    }
}