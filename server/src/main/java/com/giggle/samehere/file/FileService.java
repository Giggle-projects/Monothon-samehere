package com.giggle.samehere.file;

import com.giggle.samehere.file.exception.FileUploadException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
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
        try (InputStream inputStream = multipartFile.getInputStream()) {
            // TODO :: file duplicated check
            final FilePath filePath = FilePath.of(multipartFile);
            Files.copy(inputStream, filePath.asPathIn(directoryPath()), StandardCopyOption.REPLACE_EXISTING);
            return ROOT_PATH + filePath.asString();
        } catch (FileUploadException | IOException e) {
            // TODO :: throw new FileUploadException(e.getMessage());
            return ROOT_PATH + DEFAULT_FILE_NAME;
        }
    }

    private Path directoryPath() throws IOException {
        final Path directoryPath = Paths.get(PHOTO_UPLOAD_FOLDER);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        return directoryPath;
    }
}
