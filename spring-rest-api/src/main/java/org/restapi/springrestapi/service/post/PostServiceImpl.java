package org.restapi.springrestapi.service.post;

import java.util.List;

import org.restapi.springrestapi.dto.post.PatchPostLikeResult;
import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.PostListResult;
import org.restapi.springrestapi.dto.post.PostResult;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;
import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.model.Post;
import org.restapi.springrestapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;
	private final PostFinder postFinder;
	private final PostValidator postValidator;

	@Override
	public PostSimpleResult registerPost(Long userId, RegisterPostRequest command) {
		postValidator.validateAuthorId(userId);

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
		postValidator.validateAuthorInfo(userId, id);

		Post saved = Post.from(command, post);

		return PostResult.from(postRepository.save(saved));
	}

	@Override
	public PatchPostLikeResult updatePostLike(Long userId, Long postId) {
		Post post = postFinder.findById(postId);
		postValidator.validateAuthorId(userId);

		List<Long> likeUsers = post.getLikeUsers();
		boolean wasLikeUser = false;
		if (likeUsers != null && likeUsers.contains(userId)) {
			likeUsers.remove(userId);
			wasLikeUser = true;
		}
		if (!wasLikeUser) {
			likeUsers.add(userId);
		}

		return PatchPostLikeResult.from(postRepository.save(post), !wasLikeUser);
	}

	@Override
	public void deletePost(Long userId, Long postId) {
		postValidator.validateAuthorInfo(userId, postId);
		postRepository.deleteById(postId);
	}

}
