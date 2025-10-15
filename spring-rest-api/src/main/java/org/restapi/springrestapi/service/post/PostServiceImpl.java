package org.restapi.springrestapi.service.post;

import java.util.List;

import org.restapi.springrestapi.dto.post.PatchPostLikeResult;
import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.PostListResult;
import org.restapi.springrestapi.dto.post.PostResult;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;
import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.PostErrorCode;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.finder.UserFinder;
import org.restapi.springrestapi.model.Post;
import org.restapi.springrestapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;
	private final PostFinder postFinder;
	private final UserFinder userFinder;

	@Override
	public PostSimpleResult registerPost(Long userId, RegisterPostRequest command) {
		validateAuthorId(userId);

		Post post = Post.from(userId, command);

		return PostSimpleResult.from(postRepository.save(post));
	}

	@Override
	public PostListResult getPostList(int cursor, int limit) {
		List<PostSimpleResult> postList = postFinder.findAll(cursor, limit);
		return PostListResult.from(postList, (int)Math.max(postList.get(0).id() - 1, 0));
	}

	@Override
	public PostResult getPost(Long userId, Long id) {
		Post post = postFinder.findById(id);

		PostResult result = PostResult.from(post);
		if (userId != null && post.getLikeUsers().stream().anyMatch(_id -> _id.equals(userId))) {
			result.markLike();
		}

		return result;
	}

	@Override
	public PostResult updatePost(Long userId, Long id, PatchPostRequest command) {
		Post post = postFinder.findById(id);
		validateAuthorInfo(userId, id);

		Post saved = Post.from(command, post);

		return PostResult.from(postRepository.save(saved));
	}

	@Override
	public PatchPostLikeResult updatePostLike(Long userId, Long postId) {
		Post post = postFinder.findById(postId);
		validateAuthorId(userId);

		List<Long> likeUsers = post.getLikeUsers();
		boolean wasLikeUser = false;
		for (Long likeUserId : likeUsers) {
			if (likeUserId.equals(userId)) {
				likeUsers.remove(likeUserId);
				wasLikeUser = true;
			}
		}
		if (!wasLikeUser) {
			likeUsers.add(userId);
		}

		return PatchPostLikeResult.from(postRepository.save(post), !wasLikeUser);
	}

	@Override
	public void deletePost(Long userId, Long postId) {
		validateAuthorInfo(userId, postId);
		postRepository.deleteById(postId);
	}

	private void validateAuthorId(Long userId) {
		if (userFinder.findById(userId) == null) {
			throw new AppException(UserErrorCode.USER_NOT_FOUND);
		}
	}
	private void validateAuthorForbidden(Long userId, Long postId) {
		if (!postRepository.existsByIdAndUserId(postId, userId)) {
			throw new AppException(PostErrorCode.PERMISSION_DENIED);
		}
	}
	private void validateAuthorInfo(Long userId, Long postId) {
		validateAuthorId(userId);
		validateAuthorForbidden(userId, postId);
	}
}
