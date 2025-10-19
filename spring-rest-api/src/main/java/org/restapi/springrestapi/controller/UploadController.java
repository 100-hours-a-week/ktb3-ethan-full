package org.restapi.springrestapi.controller;

import org.restapi.springrestapi.dto.upload.UploadImageResponse;
import org.restapi.springrestapi.exception.code.SuccessCode;
import org.restapi.springrestapi.security.AuthContext;
import org.restapi.springrestapi.common.APIResponse;
import org.restapi.springrestapi.common.util.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UploadController {
	private final AuthContext auth;
	private final FileStorageService fileStorageService;

	@PostMapping(
		value = "/upload/image",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<APIResponse<UploadImageResponse>> uploadImage(
		@RequestPart("image") MultipartFile image
	) {
		final Long id = auth.requiredUserId();
		String url = fileStorageService.saveImage(id, image);
		return ResponseEntity.status(201)
			.body(APIResponse.ok(SuccessCode.REGISTER_SUCCESS, new UploadImageResponse(url)));
	}
}
