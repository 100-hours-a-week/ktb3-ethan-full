package org.restapi.springrestapi.service.comment;

import java.util.List;

import org.restapi.springrestapi.dto.comment.CommentListResult;
import org.restapi.springrestapi.dto.comment.CommentResult;
import org.restapi.springrestapi.dto.comment.PatchCommentRequest;
import org.restapi.springrestapi.dto.comment.RegisterCommentRequest;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.CommentErrorCode;
import org.restapi.springrestapi.exception.code.PostErrorCode;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.finder.CommentFinder;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.finder.UserFinder;
import org.restapi.springrestapi.model.Comment;
import org.restapi.springrestapi.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Service
@RequestMapping
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;
	private final PostFinder postFinder;
	private final UserFinder userFinder;
	private final CommentFinder commentFinder;

	@Override
	public CommentResult registerComment(Long userId, RegisterCommentRequest request, Long postId) {
		validateOrigin(userId, postId);

		Comment comment = Comment.from(request, userId, postId);

		return CommentResult.from(commentRepository.save(comment));

	}

	@Override
	public CommentListResult getCommentList(Long postId, int cursor, int limit) {
		validatePostId(postId);
		List<CommentResult> commentList = commentFinder.findAll(postId, cursor, limit);
		return CommentListResult.from(commentList, (int)Math.max(commentList.get(0).id() - 1, 0));

	}

	@Override
	public CommentResult updateComment(Long userId, PatchCommentRequest request, Long postId, Long id) {
		validateOrigin(userId, postId);
		validateCommentId(id);
		Comment comment = commentFinder.findById(id);
		comment.updateContent(request.content());
		return CommentResult.from(commentRepository.save(comment));
	}

	@Override
	public void deleteComment(Long userId, Long id, Long postId) {
		validateOrigin(userId, postId);
		validateCommentId(id);
		commentRepository.deleteById(id);
	}

	private void validateOrigin(Long userId, Long postId) {
		validateUserId(userId);
		validatePostId(postId);
	}
	private void validateUserId(Long userId) {
		if (userFinder.findById(userId) == null) {
			throw new AppException(UserErrorCode.USER_NOT_FOUND);
		}
	}
	private void validatePostId(Long postId) {
		if (postFinder.findById(postId) == null) {
			throw new AppException(PostErrorCode.POST_NOT_FOUND);
		}
	}
	private void validateCommentId(Long commentId) {
		if (commentFinder.findById(commentId) == null) {
			throw new AppException(CommentErrorCode.COMMENT_NOT_FOUND);
		}
	}
}
