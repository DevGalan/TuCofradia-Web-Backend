package com.devgalan.tucofradia.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class FileDataAccess implements FileReader, FileWriter {

    private final String resourcesPath;

    public FileDataAccess(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    @Override
    public String write(MultipartFile file, String uploadPath, String fileName) {
        try {
            var basePath = resourcesPath + uploadPath;
            if (!Files.exists(Paths.get(basePath))) {
                Files.createDirectories(Paths.get(basePath));
                System.out.println("Directory created: " + basePath);
            }
            System.out.println("Directory exists: " + basePath);
            Path path = Paths.get(basePath + fileName);
            System.out.println("Path: " + path);
            if (!Files.exists(path)) {
                System.out.println("File uploaded: " + fileName);
                Files.write(path, file.getBytes());
                return basePath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resource read(String filePath) {
        try {
            Path path = Paths.get(filePath);
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
