package org.restapi.springrestapi.repository.inmemory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.restapi.springrestapi.model.User;
import org.restapi.springrestapi.repository.UserRepository;
import org.restapi.springrestapi.common.util.SeedLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class InMemoryUserRepository implements UserRepository {
	private final SeedLoader seedLoader;
	private final Map<Long, User> db = new LinkedHashMap<>();
	private final AtomicLong seq = new AtomicLong(0);

	@Autowired
	public InMemoryUserRepository(SeedLoader seedLoader) {
		this.seedLoader = seedLoader;
	}

	@PostConstruct
	void init() {
		List<User> list = seedLoader.load("users", User.class);
		list.forEach(u -> db.put(u.getId(), u));
		seq.set(
			db.keySet().stream().mapToLong(Long::longValue)
				.max().orElse(0L) + 1
		);
	}

	@Override
	public User save(User user) {
		final Long id = (user.getId() == null ? seq.getAndIncrement() : user.getId());
		User saved = user.toBuilder()
			.id(id)
			.build();
		db.put(id, saved);
		return saved;
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(db.get(id));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return db.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
	}

	@Override
	public boolean existsByEmail(String email) {
		return db.values().stream()
			.anyMatch(u -> u.getEmail().equals(email));
	}

	@Override
	public boolean existsByNickName(String nickName) {
		return db.values().stream()
			.anyMatch(u -> u.getNickname().equals(nickName));
	}

	@Override
	public void deleteById(Long id) {
		db.remove(id);
	}
}
