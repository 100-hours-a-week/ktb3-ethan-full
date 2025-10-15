package org.restapi.springrestapi.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.restapi.springrestapi.exception.AppException;
import org.restapi.springrestapi.exception.code.CommonErrorCode;
import org.restapi.springrestapi.exception.code.UploadErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LocalFileStorageService implements FileStorageService {

	@Value("${app.upload.base-dir}")
	private String baseDir;

	@Value("${app.upload.public-base-url}")
	private String publicBaseUrl;

	private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
		"png", "jpg", "jpeg"
	);

	@Override
	public String saveImage(Long userId, MultipartFile file) {
		if (userId == null || userId <= 0 || file == null || file.isEmpty())
			throw new AppException(CommonErrorCode.INVALID_REQUEST);

		String fileName = Optional.ofNullable(
			file.getOriginalFilename()
		).orElse("file");
		String fileExtension = getFileExtension(fileName);
		if  (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
			throw new AppException(UploadErrorCode.INVALID_FILE_TYPE);
		}

		Path folder = Paths.get(baseDir, String.valueOf(userId)).normalize();
		try {
			Files.createDirectories(folder);
		} catch (IOException ignore) {}
		try {
			FileUtils.cleanDirectory(folder.toFile());
		} catch (IOException ignore) {
			throw new AppException(CommonErrorCode.INTERNAL);
		}

		fileName = "profile-img" + fileExtension;
		Path dest = folder.resolve(fileName).normalize();

		Path tmp = folder.resolve(fileName + ".tmp");
		try (InputStream in = file.getInputStream()) {
			FileUtils.copyInputStreamToFile(in, tmp.toFile());
			try {
				Files.move(tmp, dest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
			} catch (AtomicMoveNotSupportedException ex) {
				Files.move(tmp, dest, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			try {
				Files.deleteIfExists(tmp);
			} catch (IOException ignored) {}
			throw new AppException(CommonErrorCode.INTERNAL);
		}

		String ref = userId.toString() + "/" + fileName;
		return publicBaseUrl + (publicBaseUrl.endsWith("/") ? "" : "/") + ref;
	}

	private String getFileExtension(String fileName) {
		final int lastDot =  fileName.lastIndexOf('.');
		if (lastDot == -1) {
			return "png";
		}
		return fileName.substring(lastDot + 1).toLowerCase();
	}
}
