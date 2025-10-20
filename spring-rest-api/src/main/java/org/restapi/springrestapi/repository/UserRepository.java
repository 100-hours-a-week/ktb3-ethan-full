package org.restapi.springrestapi.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.restapi.springrestapi.model.User;

public interface UserRepository {
	User save(User user);
	Optional<User> findById(Long id);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByNickName(String nickName);
	void deleteById(Long id);
	List<String> findAllByIdIn(Collection<Long> ids);
}
