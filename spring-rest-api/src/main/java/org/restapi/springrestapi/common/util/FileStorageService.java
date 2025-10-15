package org.restapi.springrestapi.common.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	String saveImage(Long userId, MultipartFile file);
}
