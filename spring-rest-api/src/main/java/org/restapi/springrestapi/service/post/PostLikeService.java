package org.restapi.springrestapi.service.post;

import lombok.RequiredArgsConstructor;
import org.restapi.springrestapi.dto.post.PatchPostLikeResult;
import org.restapi.springrestapi.finder.PostFinder;
import org.restapi.springrestapi.finder.UserFinder;
import org.restapi.springrestapi.model.Post;
import org.restapi.springrestapi.model.PostLike;
import org.restapi.springrestapi.model.User;
import org.restapi.springrestapi.repository.PostLikeRepository;
import org.restapi.springrestapi.repository.PostRepository;
import org.restapi.springrestapi.validator.PostValidator;
import org.restapi.springrestapi.validator.UserValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostValidator postValidator;
    private final UserValidator userValidator;
    private final UserFinder userFinder;
    private final PostFinder postFinder;

    public PatchPostLikeResult togglePostLike(Long userId, Long postId) {
        userValidator.validateUserExists(userId);
        postValidator.validatePostExists(postId);

        final boolean wasLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        int likeCount;
        if (wasLiked) {
            PostLike postLike = postLikeRepository.findByUserIdAndPostId(userId, postId);
            postLike.unLike();
            postLikeRepository.delete(postLike);
            likeCount = postRepository.decreaseLikeCount(postId);
        } else {
            User user = userFinder.findProxyById(userId);
            Post post = postFinder.findProxyById(postId);
            PostLike like = new PostLike(user, post);

            postLikeRepository.save(like);
            likeCount = postRepository.increaseLikeCount(postId);
        }
        return PatchPostLikeResult.from(likeCount, !wasLiked);
    }
}
