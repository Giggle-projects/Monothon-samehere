package com.giggle.samehere.file.domain;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageFile {

    private final Path filePath;

    public ImageFile(Path filePath) {
        this.filePath = filePath;
    }

    public static ImageFile of(Path directory, MultipartFile multipartFile) {
        assert multipartFile.getOriginalFilename() != null;
        final String fileName = LocalDateTime.now() + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        final Path path = uniquePath(directory.resolve(fileName));
        return new ImageFile(path);
    }

    private static Path uniquePath(Path path) {
        if (Files.exists(path)) {
            return uniquePath(Path.of(path + UUID.randomUUID().toString().substring(0, 5)));
        }
        return path;
    }

    public Path path() {
        return filePath;
    }
}
