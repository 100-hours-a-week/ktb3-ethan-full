package org.restapi.springrestapi.repository.inmemory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.restapi.springrestapi.dto.user.SimpleUserInfo;
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
	private final Map<String, Long> emailToUserMap = new ConcurrentHashMap<>();
	private final Map<String, Long> nicknameToUserMap = new ConcurrentHashMap<>();
	private final AtomicLong seq = new AtomicLong(0);

	@Autowired
	public InMemoryUserRepository(SeedLoader seedLoader) {
		this.seedLoader = seedLoader;
	}

	@PostConstruct
	void init() {
		List<User> list = seedLoader.load("users", User.class);
		list.forEach(u -> {
			db.put(u.getId(), u);
			emailToUserMap.put(u.getEmail(), u.getId());
			nicknameToUserMap.put(u.getNickname(), u.getId());
		});
		seq.set(
			db.keySet().stream().mapToLong(Long::longValue)
				.max().orElse(0L) + 1
		);
	}

	@Override
	public User save(User user) {
		final Long id = (user.getId() == null ? seq.getAndIncrement() : user.getId());
		
		if (user.getId() != null) {
			User prev = db.get(user.getId());
			if (prev != null) {
				emailToUserMap.remove(prev.getEmail());
				nicknameToUserMap.remove(prev.getNickname());
			}
		}

		User saved = user.toBuilder()
			.id(id)
			.build();
		db.put(id, saved);
		emailToUserMap.put(saved.getEmail(), saved.getId());
		nicknameToUserMap.put(saved.getNickname(), saved.getId());
		return saved;
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(db.get(id));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return this.findById(emailToUserMap.get(email));
	}

	@Override
	public boolean existsByEmail(String email) {
		return emailToUserMap.containsKey(email);
	}

	@Override
	public boolean existsByNickName(String nickName) {
		return nicknameToUserMap.containsKey(nickName);
	}

	@Override
	public void deleteById(Long id) {
		User user = db.get(id);
		if (user != null) {
			emailToUserMap.remove(user.getEmail());
			nicknameToUserMap.remove(user.getNickname());
		}
		db.remove(id);
	}

    @Override
    public Map<Long, SimpleUserInfo> findSimpleInfoByIds(Collection<Long> ids) {
        return new HashSet<>(ids).stream()
                .map(db::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        User::getId,
                        u -> new SimpleUserInfo(u.getNickname())
                ));
    }

}
