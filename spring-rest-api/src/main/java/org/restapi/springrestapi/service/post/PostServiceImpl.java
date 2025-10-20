package org.restapi.springrestapi.service.post;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.restapi.springrestapi.dto.post.PatchPostLikeResult;
import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.PostListResult;
import org.restapi.springrestapi.dto.post.PostResult;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;
import org.restapi.springrestapi.dto.post.PostSimpleResult;
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
    private final PostValidator postValidator;

	@Override
	public PostSimpleResult registerPost(Long userId, RegisterPostRequest command) {
		postValidator.validateAuthorId(userId);

		Post post = Post.from(userId, command);

		return PostSimpleResult.from(postRepository.save(post));
	}

    @Override
    public PostListResult getPostList(int cursor, int limit) {
        List<PostSimpleResult> posts = postFinder.findAll(cursor, limit);
        if (posts.isEmpty()) {
            return PostListResult.from(List.of(), cursor);
        }

        Set<Long> userIds = posts.stream()
                .map(PostSimpleResult::userId)
                .collect(Collectors.toSet());

        Map<Long, String> nicknameByUserId = userFinder.findNicknamesByIds(userIds);

        List<PostSimpleResult> enriched = posts.stream()
                .map(p -> PostSimpleResult.from(
                        p,
                        nicknameByUserId.getOrDefault(p.userId(), "(unknown)") // 방어
                ))
                .toList();

        int nextCursor = calcNextCursor(enriched);

        return PostListResult.from(enriched, nextCursor);
    }

    private int calcNextCursor(List<PostSimpleResult> posts) {
        // 정렬 규칙에 맞게 조정하자.
        // 예: id DESC 페이지라면 마지막 요소의 id - 1
        long lastIdDesc = posts.get(posts.size() - 1).id();
        return (int) Math.max(lastIdDesc - 1, 0);
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
