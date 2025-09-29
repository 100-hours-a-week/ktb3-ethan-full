package com.example.spring_practice.dto;

public class UserDto {
	private String username;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDto{" +
			"username='" + username + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
