package org.restapi.springrestapi.service.auth;

import org.restapi.springrestapi.config.PasswordEncoder;
import org.restapi.springrestapi.dto.auth.LoginResult;
import org.restapi.springrestapi.dto.user.LoginRequest;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.AuthErrorCode;
import org.restapi.springrestapi.model.User;

import org.restapi.springrestapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginResult login(LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.email())
			.orElseThrow(() -> new AppException(AuthErrorCode.INVALID_EMAIL_OR_PASSWORD));
		if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
			throw new AppException(AuthErrorCode.INVALID_EMAIL_OR_PASSWORD);
		}

		return LoginResult.builder()
			.userId(user.getId())
			.nickname(user.getNickname())
			.accessToken(user.getId().toString())
			.build();
	}
}
