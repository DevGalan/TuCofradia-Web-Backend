package com.devgalan.tucofradia.services.image;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploaderServiceImpl implements ImageUploaderService {

    private final String RESOURCES_PATH = "src/main/resources/static/";

    @Override
    public String uploadImage(MultipartFile file, String uploadPath, String imageName) {
        try {
            var basePath = RESOURCES_PATH + uploadPath;
            if (!Files.exists(Paths.get(basePath))) {
                Files.createDirectories(Paths.get(basePath));
                System.out.println("Directory created: " + basePath);
            }
            System.out.println("Directory exists: " + basePath);
            Path path = Paths.get(basePath + imageName);
            System.out.println("Path: " + path);
            if (!Files.exists(path)) {
                System.out.println("File uploaded: " + imageName);
                Files.write(path, file.getBytes());
                return basePath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteImage(String imagePath) {
        try {
            System.out.println("Deleting image: " + imagePath);
            Path path = Paths.get(imagePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resource getUploadedImage(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            Resource resource = new UrlResource(path.toUri());
            if (Files.exists(path) && resource.isReadable()) {
                return resource;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
