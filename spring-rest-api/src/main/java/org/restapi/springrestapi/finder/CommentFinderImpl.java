package org.restapi.springrestapi.finder;

import java.util.List;

import org.restapi.springrestapi.dto.comment.CommentResult;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.CommentErrorCode;
import org.restapi.springrestapi.model.Comment;
import org.restapi.springrestapi.repository.CommentRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentFinderImpl implements CommentFinder {
	private final CommentRepository commentRepository;

	@Override
	public Comment findById(Long id) {
		return commentRepository.findById(id)
			.orElseThrow(() -> new AppException(CommentErrorCode.COMMENT_NOT_FOUND));
	}

	@Override
	public List<CommentResult> findAll(Long postId, int cursor, int limit) {
		return commentRepository.findAll(postId, cursor, limit).stream().map(CommentResult::from).toList();
	}

	@Override
	public boolean existsById(Long id) {
		return commentRepository.findById(id).isPresent();
	}

    @Override
    public boolean existsByIdAndUserId(Long id, Long userId) {
        return commentRepository.existsByIdAndUserId(id, userId);
    }
}
