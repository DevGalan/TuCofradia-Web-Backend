package com.devgalan.tucofradia.data;

import org.springframework.web.multipart.MultipartFile;

public interface FileWriter {
    String write(MultipartFile file, String uploadPath, String fileName);
    void delete(String path);
}
