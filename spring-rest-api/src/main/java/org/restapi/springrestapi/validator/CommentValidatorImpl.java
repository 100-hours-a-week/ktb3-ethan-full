package org.restapi.springrestapi.validator;

import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.CommentErrorCode;
import org.restapi.springrestapi.finder.CommentFinder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentValidatorImpl implements CommentValidator {
    private final CommentFinder commentFinder;

    @Override
    public void validateCommentExists(Long commendId) {
        if (!commentFinder.existsById(commendId)) {
            throw new AppException(CommentErrorCode.COMMENT_NOT_FOUND);
        }
    }

    @Override
    public void validateOwner(Long commentId, Long userId) {
        if (!commentFinder.existsByIdAndUserId(commentId, userId)) {
            throw new AppException(CommentErrorCode.COMMENT_FORBIDDEN);
        }
    }
}