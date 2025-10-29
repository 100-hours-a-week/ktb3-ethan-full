package org.restapi.springrestapi.finder;

import org.restapi.springrestapi.dto.user.SimpleUserInfo;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.model.User;
import org.restapi.springrestapi.repository.UserRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFinderImpl implements UserFinder {
	private final UserRepository userRepository;

	@Override
	public User findById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new AppException(UserErrorCode.USER_NOT_FOUND));
	}

    @Override
    public User findProxyById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByNickName(String nickName) {
		return userRepository.existsByNickname(nickName);
	}

    @Override
    public Map<Long, SimpleUserInfo> findSimpleInfoByIds(Collection<Long> userIds) {
        return userRepository.findSimpleInfoByIds(userIds);
    }
}
