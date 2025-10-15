package org.restapi.springrestapi.dto.user;

public record LoginRequest(
	String email,
	String password
) {

}
