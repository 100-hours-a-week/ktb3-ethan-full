package org.restapi.springrestapi.service.post;

import java.util.List;
import java.util.Map;

import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.PostListResult;
import org.restapi.springrestapi.dto.post.PostResult;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;
import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.dto.user.SimpleUserInfo;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.finder.UserFinder;
import org.restapi.springrestapi.model.Post;
import org.restapi.springrestapi.repository.PostRepository;
import org.restapi.springrestapi.model.User;
import org.restapi.springrestapi.validator.PostValidator;
import org.restapi.springrestapi.validator.UserValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;

	private final PostFinder postFinder;
    private final UserFinder userFinder;
    private final PostValidator postValidator;
    private final UserValidator userValidator;

    @Override
	public PostSimpleResult registerPost(Long userId, RegisterPostRequest command) {
		userValidator.validateUserExists(userId);

        User user = userFinder.findProxyById(userId);
		Post post = Post.from(command);
        post.changeAuthor(user);

		return PostSimpleResult.from(postRepository.save(post));
	}

    @Override
    public PostListResult getPostList(int cursor, int limit) {


        postRepository.findLatestIds(PageRequest.of(0, 1));

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

        final boolean didLike = (
                userId != null &&
                        post.getLikes().stream().anyMatch(like -> like.getId().equals(userId))
        );

		return PostResult.from(post, didLike);
	}

	@Override
	public PostResult updatePost(Long userId, Long id, PatchPostRequest command) {
		Post post = postFinder.findById(id);
		postValidator.validateAuthor(userId, id);

		Post saved = Post.from(command, post);

		return PostResult.from(postRepository.save(saved));
	}

	@Override
	public void deletePost(Long userId, Long postId) {
		postValidator.validateAuthor(userId, postId);

        (postFinder.findProxyById(postId)).changeAuthor(null);

		postRepository.deleteById(postId);
	}
}
