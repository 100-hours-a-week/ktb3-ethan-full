package org.restapi.springrestapi.dto.user;

public record PatchProfileResponse(
	String nickname,
	String profileImage
) {
	public static PatchProfileResponse from(PatchProfileRequest request) {
		return new PatchProfileResponse(
			request.getNickname(),
			request.getProfileImage()
		);
	}
}
