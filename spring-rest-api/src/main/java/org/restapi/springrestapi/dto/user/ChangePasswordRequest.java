package org.restapi.springrestapi.dto.user;

import org.restapi.springrestapi.common.annotation.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
	@ValidPassword
	private String password;

	@NotBlank(message = "password_is_requireed")
	private String confirmPassword;
}
