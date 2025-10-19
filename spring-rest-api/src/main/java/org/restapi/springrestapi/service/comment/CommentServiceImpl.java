package org.restapi.springrestapi.service.comment;

import java.util.List;

import org.restapi.springrestapi.dto.comment.CommentListResult;
import org.restapi.springrestapi.dto.comment.CommentResult;
import org.restapi.springrestapi.dto.comment.PatchCommentRequest;
import org.restapi.springrestapi.dto.comment.RegisterCommentRequest;
import org.restapi.springrestapi.finder.CommentFinder;
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
	private final CommentFinder commentFinder;
	private final CommentValidator commentValidator;

	@Override
	public CommentResult registerComment(Long userId, RegisterCommentRequest request, Long postId) {
		commentValidator.validateOrigin(userId, postId);

		Comment comment = Comment.from(request, userId, postId);

		return CommentResult.from(commentRepository.save(comment));

	}

	@Override
	public CommentListResult getCommentList(Long postId, int cursor, int limit) {
		commentValidator.validatePost(postId);
		List<CommentResult> commentList = commentFinder.findAll(postId, cursor, limit);
		return CommentListResult.from(commentList, (int)Math.max(commentList.get(0).id() - 1, 0));

	}

	@Override
	public CommentResult updateComment(Long userId, PatchCommentRequest request, Long postId, Long id) {
		commentValidator.validateOrigin(userId, postId);
		commentValidator.validateComment(id);
		Comment comment = commentFinder.findById(id);
		comment.updateContent(request.content());
		return CommentResult.from(commentRepository.save(comment));
	}

	@Override
	public void deleteComment(Long userId, Long id, Long postId) {
		commentValidator.validateOrigin(userId, postId);
		commentValidator.validateComment(id);
		commentRepository.deleteById(id);
	}
}
