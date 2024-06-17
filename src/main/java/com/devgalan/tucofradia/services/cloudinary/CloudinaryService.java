package com.devgalan.tucofradia.services.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadImage(MultipartFile image, String folderName);
    void deleteImage(String url);
}
