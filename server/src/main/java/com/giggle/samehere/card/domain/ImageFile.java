package com.giggle.samehere.card.domain;

import com.giggle.samehere.card.exception.FileUploadException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageFile {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");

    private final Path filePath;

    private ImageFile(Path filePath) {
        this.filePath = filePath;
    }

    public static ImageFile pathOf(String filePath) {
        return new ImageFile(Path.of(filePath));
    }

    public static ImageFile save(Path directoryPath, MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            final Path filePath = filePath(directoryPath, multipartFile);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return new ImageFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException();
        }
    }

    public static ImageFile save(String directoryPath, MultipartFile multipartFile) {
        return save(Path.of(directoryPath), multipartFile);
    }

    private static Path filePath(Path directoryPath, MultipartFile multipartFile) {
        final String prefix = LocalDateTime.now().format(DATE_TIME_FORMATTER) + ":";
        final String fileName = prefix + StringUtils.cleanPath(multipartFile.getOriginalFilename());
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
