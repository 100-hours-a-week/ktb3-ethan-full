package org.restapi.springrestapi.service.post;

import org.restapi.springrestapi.dto.post.PatchPostLikeResult;
import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.PostListResult;
import org.restapi.springrestapi.dto.post.PostResult;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;
import org.restapi.springrestapi.dto.post.PostSimpleResult;

public interface PostService {
	PostSimpleResult registerPost(Long userId, RegisterPostRequest command);
	PostListResult getPostList(int cursor, int limit);
	PostResult getPost(Long userId, Long id);
	PostResult updatePost(Long userId, Long id, PatchPostRequest command);
	PatchPostLikeResult updatePostLike(Long userId, Long postId);
	void deletePost(Long userId, Long postId);
}
