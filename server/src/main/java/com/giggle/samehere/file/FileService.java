package com.giggle.samehere.file;

import com.giggle.samehere.file.exception.FileUploadException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${cards.profile.image.resource.root}")
    private String ROOT_PATH;

    @Value("${cards.profile.image.default.name}")
    private String DEFAULT_FILE_NAME;

    @Value("${cards.profile.image.upload.folder}")
    private String PHOTO_UPLOAD_FOLDER;

    public String saveImageFile(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
            throw new FileUploadException();
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            final MultipartFileName fileName = MultipartFileName.of(multipartFile);
            saveFile(inputStream, fileName.asPathIn(directoryPath()));
            return ROOT_PATH + "/" + fileName.asString();
        } catch (IOException e) {
            throw new FileUploadException();
        }
    }

    private void saveFile(InputStream inputStream, Path path) throws IOException {
        final Path uniquePath = findUniquePath(path);
        Files.copy(inputStream, uniquePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private Path findUniquePath(Path path) {
        if (Files.exists(path)) {
            return findUniquePath(Path.of(path + UUID.randomUUID().toString().substring(0, 5)));
        }
        return path;
    }

    private Path directoryPath() throws IOException {
        final Path directoryPath = Paths.get(PHOTO_UPLOAD_FOLDER);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        return directoryPath;
    }
}
