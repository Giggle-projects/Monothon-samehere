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
        final ImageFile imageFile = ImageFile.of(directory(), multipartFile);
        return ImageFileResponse.of(imageFile);
    }

    private Path directory() throws IOException {
        final Path directoryPath = Paths.get(PHOTO_UPLOAD_FOLDER);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        return directoryPath;
    }
}
