package org.restapi.springrestapi.dto.auth;

import lombok.Builder;


@Builder
public record LoginResult(
	Long userId,
	String nickname,
	String accessToken
) {

}
