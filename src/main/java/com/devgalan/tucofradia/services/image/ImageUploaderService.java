package com.devgalan.tucofradia.services.image;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.devgalan.tucofradia.models.UploadedImage;

public interface ImageUploaderService {
    UploadedImage uploadImage(UploadedImage uploadedImage, MultipartFile image, String imagesPath, String serverUrl, Long userId);
    void deleteImage(String imagePath);
    Resource getUploadedImage(String imagePath);
}
