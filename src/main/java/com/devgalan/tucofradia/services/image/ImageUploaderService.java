package com.devgalan.tucofradia.services.image;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploaderService {
    void uploadImage(MultipartFile file, String uploadPath);
    void deleteImage(String imagePath);
    Resource getUploadedImage(String imageName);
}
