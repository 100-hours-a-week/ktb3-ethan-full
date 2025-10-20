package org.restapi.springrestapi.finder;

import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.model.User;
import org.restapi.springrestapi.repository.UserRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserFinderImpl implements UserFinder {
	private final UserRepository userRepository;

	@Override
	public User findById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new AppException(UserErrorCode.USER_NOT_FOUND));
	}

	@Override
	public boolean existsById(Long id) {
		return userRepository.findById(id).isPresent();
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByNickName(String nickName) {
		return userRepository.existsByNickName(nickName);
	}

    @Override
    public Map<Long, String> findNicknamesByIds(Collection<Long> ids) {
        return userRepository.findAllByIdIn(ids).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        User::getId,
                        User::getNickname
                )).toMap();

    }
}
