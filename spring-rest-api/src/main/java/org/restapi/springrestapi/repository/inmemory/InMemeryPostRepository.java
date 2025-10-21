package org.restapi.springrestapi.repository.inmemory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
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
    private final Map<Long, Long> userIdToPostMap = new LinkedHashMap<>();
	private final AtomicLong seq = new AtomicLong(0);

	@Autowired
	public InMemeryPostRepository(SeedLoader seedLoader) {
		this.seedLoader = seedLoader;
	}

	@PostConstruct
	void init() {
		List<Post> list = seedLoader.load("posts", Post.class);
		list.forEach(p -> {
            db.put(p.getId(), p);
            userIdToPostMap.put(p.getUserId(), p.getId());
        });
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
        userIdToPostMap.put(saved.getUserId(), id);
		return saved;
	}

	@Override
	public Optional<Post> findById(Long id) {
		return Optional.ofNullable(db.get(id));
	}

	@Override
	public List<Post> findAll(int cursor, int limit) {
		List<Post> posts = new ArrayList<>(db.values());
        Collections.reverse(posts);

        return posts.stream()
            .skip(cursor)
            .limit(limit)
            .collect(Collectors.toList());
	}

	@Override
	public boolean existsByIdAndUserId(Long id, Long userId) {
        return userIdToPostMap.containsKey(userId) &&
                userIdToPostMap.get(userId).equals(id);
	}

	@Override
	public void deleteById(Long id) {
        userIdToPostMap.remove(db.get(id).getUserId());
        db.remove(id);
	}
}
