package com.giggle.samehere.file.domain;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

import com.giggle.samehere.file.dto.ImageFileResponse;
import com.giggle.samehere.file.exception.FileUploadException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageFile {

    private final Path filePath;

    public ImageFile(Path filePath) {
        this.filePath = filePath;
    }

    public static ImageFile of(Path directory, MultipartFile multipartFile) {
        assert multipartFile.getOriginalFilename() != null;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            final ImageFile imageFile = new ImageFile(directory);
            Files.copy(inputStream, uniquePath(directory, multipartFile), StandardCopyOption.REPLACE_EXISTING);
            return imageFile;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException();
        }
    }

    private static Path uniquePath(Path directoryPath, MultipartFile multipartFile) {
        final String fileName = LocalDateTime.now() + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        return uniquePath(directoryPath.resolve(fileName));
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
