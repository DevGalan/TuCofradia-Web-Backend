package com.devgalan.tucofradia.services.image;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploaderService {
    String uploadImage(MultipartFile file, String uploadPath, String imageName);
    void deleteImage(String imagePath);
    Resource getUploadedImage(String imagePath);
}
