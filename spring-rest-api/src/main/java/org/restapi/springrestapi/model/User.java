package org.restapi.springrestapi.model;

import org.restapi.springrestapi.config.PasswordEncoder;
import org.restapi.springrestapi.dto.user.PatchProfileRequest;
import org.restapi.springrestapi.dto.user.RegisterUserRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class User {
	private final Long id;
	private String email;
	private String password;
	private String nickname;
	private String profileImage; // nullable

	public static User from(
		RegisterUserRequest command,
		PasswordEncoder passwordEncoder
	) {
		return User.builder()
			.email(command.getEmail())
			.password(passwordEncoder.encode(command.getPassword()))
			.nickname(command.getNickname())
			.profileImage(command.getProfileImage())
			.build();
	}
	public void updateProfile(PatchProfileRequest command) {
		this.nickname = command.getNickname();
		if (command.getProfileImage() != null) {
			this.profileImage = command.getProfileImage();
		}
	}
	public void updatePassword(String password, PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}
}
