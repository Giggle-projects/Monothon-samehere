package com.giggle.samehere.card.dto;

import com.giggle.samehere.card.domain.ImageFile;

public class ImageFileResponse {

    private final String path;

    public ImageFileResponse(String path) {
        this.path = path;
    }

    public static ImageFileResponse of(ImageFile imageFile) {
        return new ImageFileResponse(imageFile.path().toString());
    }

    public String getPath() {
        return path;
    }
}
