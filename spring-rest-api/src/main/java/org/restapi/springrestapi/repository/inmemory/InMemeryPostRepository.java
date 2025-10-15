package org.restapi.springrestapi.repository.inmemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.restapi.springrestapi.common.util.SeedLoader;
import org.restapi.springrestapi.model.Post;
import org.restapi.springrestapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class InMemeryPostRepository implements PostRepository {
	private final SeedLoader seedLoader;
	private final Map<Long, Post> db = new LinkedHashMap<>();
	private final AtomicLong seq = new AtomicLong(0);

	@Autowired
	public InMemeryPostRepository(SeedLoader seedLoader) {
		this.seedLoader = seedLoader;
	}

	@PostConstruct
	void init() {
		List<Post> list = seedLoader.load("posts", Post.class);
		list.forEach(p -> db.put(p.getId(), p));
		seq.set(
			db.keySet().stream().mapToLong(Long::longValue)
				.max().orElse(0L) + 1
		);
	}
	@Override
	public Post save(Post post) {
		final Long id = (post.getId() == null) ? seq.getAndIncrement() : post.getId() ;
		Post saved = post.toBuilder()
			.id(id)
			.build();
		db.put(id, saved);
		return saved;
	}

	@Override
	public Optional<Post> findById(Long id) {
		return Optional.ofNullable(db.get(id));
	}

	@Override
	public List<Post> findAll(int cursor, int limit) {
		final long SIZE = seq.get();
		List<Post> allPosts = new ArrayList<>(db.values());

		if (SIZE >= cursor + limit) {
			final int endIndex = Math.toIntExact(SIZE - cursor < 0 ? 0 : SIZE - cursor);
			final int startIndex = Math.max(endIndex - limit, 0);
			allPosts = allPosts.subList(startIndex, endIndex);
		}

		allPosts.sort(Collections.reverseOrder());
		return allPosts;
	}

	@Override
	public boolean existsByIdAndUserId(Long id, Long userId) {
		return db.values().stream().anyMatch(p -> p.getId().equals(id) && p.getUserId().equals(userId));
	}

	@Override
	public void deleteById(Long id) {
		db.remove(id);
	}
}
