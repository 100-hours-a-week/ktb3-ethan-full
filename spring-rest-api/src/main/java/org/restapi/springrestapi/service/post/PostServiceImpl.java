package org.restapi.springrestapi.service.post;

import java.util.List;
import java.util.Map;

import org.restapi.springrestapi.dto.post.PatchPostLikeResult;
import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.PostListResult;
import org.restapi.springrestapi.dto.post.PostResult;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;
import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.dto.user.SimpleUserInfo;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.finder.UserFinder;
import org.restapi.springrestapi.model.Post;
import org.restapi.springrestapi.legacy.repository.PostRepository;
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
        // cursor 번째 최근 글 ~ limit 개의 글, 엣지케이스는 래포지토리 계층에서 처리.
        List<PostSimpleResult> posts = postFinder.findAll(cursor, limit);
        if (posts.isEmpty()) {
            return PostListResult.from(List.of(), cursor);
        }


        List<Long> userIds = posts.stream()
                .map(PostSimpleResult::userId)
                .toList();
        // 게시글 목록 조회시 가져올 사용자 간편 정보.
        Map<Long, SimpleUserInfo> simpleUserInfoByUserId = userFinder.findSimpleInfoByIds(userIds);

        // 반환할 최종 게시글 목록
        posts = posts.stream()
                .map(p -> PostSimpleResult.from(
                        p,
                        simpleUserInfoByUserId.getOrDefault(
                                p.userId(),
                                SimpleUserInfo.unknown())
                )).toList();

        int nextCursor = calcNextCursor(posts);

        return PostListResult.from(posts, nextCursor);
    }

    private int calcNextCursor(List<PostSimpleResult> posts) {
        long lastIdDesc = posts.get(posts.size() - 1).id();
        return (int) Math.max(lastIdDesc - 1, 0);
    }

	@Override
	public PostResult getPost(Long userId, Long id) {
		Post post = postFinder.findById(id);

        final boolean didLike = userId != null && post.getLikeUsers().stream().anyMatch(_id -> _id.equals(userId));

		return PostResult.from(post, didLike);
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
