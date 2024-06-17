package com.devgalan.tucofradia.services.image;

import com.devgalan.tucofradia.models.ImageModel;
import com.devgalan.tucofradia.models.UploadedImage;

public interface ImageUploaderService {
    UploadedImage uploadImage(UploadedImage uploadedImage, ImageModel imageModel, Long userId);
    void deleteImage(String imagePath);
}
