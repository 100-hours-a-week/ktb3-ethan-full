package org.restapi.springrestapi.service.comment;

import java.util.List;

import org.restapi.springrestapi.dto.comment.CommentListResult;
import org.restapi.springrestapi.dto.comment.CommentResult;
import org.restapi.springrestapi.dto.comment.PatchCommentRequest;
import org.restapi.springrestapi.dto.comment.RegisterCommentRequest;
import org.restapi.springrestapi.finder.CommentFinder;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.model.Comment;
import org.restapi.springrestapi.repository.CommentRepository;
import org.restapi.springrestapi.model.Post;
import org.restapi.springrestapi.repository.PostRepository;
import org.restapi.springrestapi.validator.CommentValidator;
import org.restapi.springrestapi.validator.PostValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;
    private final PostRepository postRepository;

	private final CommentFinder commentFinder;
	private final CommentValidator commentValidator;
    private final PostFinder postFinder;
    private final PostValidator postValidator;

    @Override
	public CommentResult registerComment(Long userId, RegisterCommentRequest request, Long postId) {
		commentValidator.validateOrigin(userId, postId);

        Post post = postFinder.findProxyById(postId);
		Comment comment = Comment.from(request, post);
        postRepository.increaseCommentCount(postId);

		return CommentResult.from(commentRepository.save(comment));
	}

	@Override
	public CommentListResult getCommentList(Long postId, int cursor, int limit) {
		postValidator.validatePostExists(postId);

		List<CommentResult> commentList = commentFinder.findAll(postId, cursor, limit);

		if (commentList.isEmpty()) {
			return CommentListResult.empty();
		}

		return CommentListResult.from(commentList, (int)Math.max(commentList.get(0).id() - 1, 0));

	}

	@Override
	public CommentResult updateComment(Long userId, PatchCommentRequest request, Long postId, Long id) {
		commentValidator.validateOrigin(userId, postId);
        commentValidator.validateOwner(userId, id);

		Comment comment = commentFinder.findById(id);
		comment.updateContent(request.content());

		return CommentResult.from(commentRepository.save(comment));
	}

	@Override
	public void deleteComment(Long userId, Long id, Long postId) {
		commentValidator.validateOrigin(userId, postId);
        commentValidator.validateOwner(userId, id);

        postRepository.decreaseCommentCount(postId);
		commentRepository.deleteById(id);
	}
}
