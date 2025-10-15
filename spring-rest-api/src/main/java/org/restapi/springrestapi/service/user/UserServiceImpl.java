package org.restapi.springrestapi.service.user;

import org.restapi.springrestapi.config.PasswordEncoder;
import org.restapi.springrestapi.dto.user.ChangePasswordRequest;
import org.restapi.springrestapi.dto.user.PatchProfileRequest;
import org.restapi.springrestapi.dto.user.RegisterUserRequest;
import org.restapi.springrestapi.dto.user.UserProfileResult;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.UserErrorCode;
import org.restapi.springrestapi.finder.UserFinder;
import org.restapi.springrestapi.model.User;
import org.restapi.springrestapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserFinder userFinder;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Long register(RegisterUserRequest command) {
		validateDuplicateUserInfo(command.getEmail(), command.getPassword());

		User user = User.from(command, passwordEncoder);

		return userRepository.save(user).getId();
	}

	@Override
	public UserProfileResult getUserProfile(Long id) {
		return UserProfileResult.from(userFinder.findById(id));
	}

	@Override
	public void updateProfile(Long id, PatchProfileRequest request) {
		validateDuplicateNickname(request.getNickname());

		User user = userFinder.findById(id);
		user.updateProfile(request);

		userRepository.save(user);
	}

	@Override
	public void changePassword(Long id, ChangePasswordRequest request) {
		validateSamePassword(request.getPassword(), request.getConfirmPassword());

		User user = userFinder.findById(id);
		user.updatePassword(request.getPassword(), passwordEncoder);

		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	private void validateDuplicateUserInfo(String email, String nickname) {
		validateDuplicateEmail(email);
		validateDuplicateNickname(nickname);
	}
	private void validateDuplicateEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new AppException(UserErrorCode.EMAIL_CONFLICT);
		}
	}
	private void validateDuplicateNickname(String nickname) {
		if (userRepository.existsByNickName(nickname)) {
			throw new AppException(UserErrorCode.NICKNAME_CONFLICT);
		}
	}
	private void validateSamePassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			throw new AppException(UserErrorCode.NOT_MATCH_NEW_PASSWORD);
		}
	}
}
