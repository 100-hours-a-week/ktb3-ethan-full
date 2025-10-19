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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Upload", description = "파일 업로드 API")
public class UploadController {
	private final AuthContext auth;
	private final FileStorageService fileStorageService;

	@Operation(summary = "이미지 업로드", description = "로그인 사용자의 이미지를 업로드하고 URL을 반환합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "업로드 성공"),
		@ApiResponse(responseCode = "401", description = "인증 필요"),
		@ApiResponse(responseCode = "400", description = "잘못된 파일 형식 또는 요청")
	})
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
