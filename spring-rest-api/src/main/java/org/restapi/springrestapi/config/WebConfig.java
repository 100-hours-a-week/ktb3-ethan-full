package org.restapi.springrestapi.config;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable
public class WebConfig implements WebMvcConfigurer {
	@Value("${app.upload.base-dir}")
	private String baseDir;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**")
			.addResourceLocations("file: " + Paths.get(baseDir).toAbsolutePath() + "/");
	}
}
