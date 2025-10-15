package org.restapi.springrestapi.finder;

import java.util.List;

import org.restapi.springrestapi.dto.post.PostSimpleResult;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.PostErrorCode;
import org.restapi.springrestapi.model.Post;
import org.restapi.springrestapi.repository.PostRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostFinderImpl implements PostFinder {
	private final PostRepository postRepository;

	@Override
	public Post findById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new AppException(PostErrorCode.POST_NOT_FOUND));
	}

	@Override
	public List<PostSimpleResult> findAll(int cursor, int limit) {
		return postRepository.findAll(cursor, limit).stream().map(PostSimpleResult::from).toList();
	}
}
