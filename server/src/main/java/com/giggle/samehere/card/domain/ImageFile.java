package com.giggle.samehere.card.domain;

import com.giggle.samehere.card.exception.FileUploadException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

public class ImageFile {

    private final Path filePath;

    private ImageFile(Path filePath) {
        this.filePath = filePath;
    }

    public static ImageFile save(Path filePath, MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath);
            }
            Files.copy(inputStream, filePath(filePath, multipartFile), StandardCopyOption.REPLACE_EXISTING);
            return new ImageFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException();
        }
    }

    private static Path filePath(Path directoryPath, MultipartFile multipartFile) {
        final String fileName = LocalDateTime.now() + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        final Path path = directoryPath.resolve(fileName);
        return uniquePath(path);
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
