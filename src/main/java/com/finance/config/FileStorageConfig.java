package com.finance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import java.nio.file.*;

@Configuration
public class FileStorageConfig {
	@Bean
	public Path uploadPath(@Value("${app.file.upload-dir:uploads}") String dir) throws Exception {
		Path path = Paths.get(dir).toAbsolutePath().normalize();
		Files.createDirectories(path);
		return path;
	}
}
