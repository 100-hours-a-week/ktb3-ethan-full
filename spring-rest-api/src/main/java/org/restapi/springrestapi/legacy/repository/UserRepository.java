package org.restapi.springrestapi.legacy.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.restapi.springrestapi.dto.user.SimpleUserInfo;
import org.restapi.springrestapi.model.User;

public interface UserRepository {
	User save(User user);
	Optional<User> findById(Long id);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByNickName(String nickName);
	void deleteById(Long id);
	Map<Long, SimpleUserInfo> findSimpleInfoByIds(Collection<Long> ids);
}
