package com.giggle.samehere.file.application;

import com.giggle.samehere.file.domain.ImageFile;
import com.giggle.samehere.file.dto.ImageFileResponse;
import com.giggle.samehere.file.exception.FileUploadException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageFileService {

    @Value("${cards.profile.image.upload.folder}")
    private String PHOTO_UPLOAD_FOLDER;

    public ImageFileResponse save(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            final ImageFile imageFile = ImageFile.of(directory(), multipartFile);
            Files.copy(inputStream, imageFile.path(), StandardCopyOption.REPLACE_EXISTING);
            return ImageFileResponse.of(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException();
        }
    }

    private Path directory() throws IOException {
        final Path directoryPath = Paths.get(PHOTO_UPLOAD_FOLDER);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        return directoryPath;
    }
}
