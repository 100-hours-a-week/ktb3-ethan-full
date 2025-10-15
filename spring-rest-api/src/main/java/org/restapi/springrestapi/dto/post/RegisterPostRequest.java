package org.restapi.springrestapi.dto.post;

import org.restapi.springrestapi.common.annotation.ValidPostTitle;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterPostRequest {
	@ValidPostTitle
	private String title;

	@NotBlank
	private String content;

	private String postImage; // nullable
}
