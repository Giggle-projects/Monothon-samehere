package com.giggle.samehere.file;

import com.giggle.samehere.file.exception.FileUploadException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileName {

    private final String fileName;

    public MultipartFileName(String fileName) {
        this.fileName = fileName;
    }

    public static MultipartFileName of(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
            throw new FileUploadException();
        }
        final String uniqueFileName = LocalDateTime.now() + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        return new MultipartFileName(uniqueFileName);
    }

    public Path asPathIn(Path path) {
        return path.resolve(fileName);
    }

    public String asString() {
        return fileName;
    }
}
