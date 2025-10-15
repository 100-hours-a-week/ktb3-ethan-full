package org.restapi.springrestapi.finder;

import java.util.List;

import org.restapi.springrestapi.dto.comment.CommentResult;
import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.CommentErrorCode;
import org.restapi.springrestapi.model.Comment;
import org.restapi.springrestapi.repository.CommentRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
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
}
