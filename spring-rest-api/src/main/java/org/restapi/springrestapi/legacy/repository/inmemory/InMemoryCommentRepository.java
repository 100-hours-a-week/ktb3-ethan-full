package org.restapi.springrestapi.legacy.repository.inmemory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.restapi.springrestapi.common.util.SeedLoader;
import org.restapi.springrestapi.model.Comment;
import org.restapi.springrestapi.legacy.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class InMemoryCommentRepository implements CommentRepository {
	private final SeedLoader seedLoader;
	private final Map<Long, Comment> db = new LinkedHashMap<>();
	private final AtomicLong seq = new AtomicLong(0);

	@Autowired
	public InMemoryCommentRepository(SeedLoader seedLoader) {
		this.seedLoader = seedLoader;
	}

	@PostConstruct
	void init() {
		List<Comment> list = seedLoader.load("comments", Comment.class);
		list.forEach(c -> db.put(c.getId(), c));
		seq.set(
			db.keySet().stream().mapToLong(Long::longValue)
				.max().orElse(0L) + 1
		);
	}


	@Override
	public Comment save(Comment comment) {
		final long id = (comment.getId() == null ? seq.getAndIncrement() : comment.getId());
		Comment saved = comment.toBuilder()
			.id(id)
			.build();
		db.put(id, saved);
		return saved;
	}

	@Override
	public Optional<Comment> findById(Long id) {
		return Optional.ofNullable(db.get(id));
	}

	@Override
	public List<Comment> findAll(Long postId, int cursor, int limit) {
		final long SIZE = seq.get();
		List<Comment> allComments = new ArrayList<>(
			db.values().stream().filter(c -> c.getPostId().equals(postId)).toList()
		);

		if (SIZE >= cursor + limit) {
			final int endIndex = Math.toIntExact(SIZE - cursor < 0 ? 0 : SIZE - cursor);
			final int startIndex = Math.max(endIndex - limit, 0);
			allComments = allComments.subList(startIndex, endIndex);
		}

		allComments.sort(Comparator.comparing(Comment::getId).reversed());
		return allComments;
	}

	@Override
	public void deleteById(Long id) {
		db.remove(id);
	}
}
