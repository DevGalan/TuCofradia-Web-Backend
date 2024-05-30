package com.devgalan.tucofradia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devgalan.tucofradia.models.New;
import com.devgalan.tucofradia.repositories.UploadedImageRepository;
import com.devgalan.tucofradia.services.image.ImageUploaderService;
import com.devgalan.tucofradia.services.news.NewService;

@RestController
@RequestMapping("api/news")
public class NewController {

    private final NewService newService;

    private final ImageUploaderService imageUploaderService;

    private final UploadedImageRepository uploadedImageRepository;

    private final String IMAGES_PATH = "uploaded_images/images_of_news/";

    @Value("${server.url}")
    private String SERVER_URL;

    public NewController(NewService newService, ImageUploaderService imageUploaderService,
            UploadedImageRepository uploadedImageRepository) {
        this.newService = newService;
        this.imageUploaderService = imageUploaderService;
        this.uploadedImageRepository = uploadedImageRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<New>> getNews() {
        return ResponseEntity.ok(newService.getNews());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNew(New createNew) {
        newService.createNew(createNew);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<New> updateNew(@PathVariable Long id, New updateNew) {
        var dataBaseNew = newService.getNewById(id);
        if (dataBaseNew.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        dataBaseNew.get().setTitle(updateNew.getTitle());
        dataBaseNew.get().setBody(updateNew.getBody());
        dataBaseNew.get().setDate(updateNew.getDate());
        return ResponseEntity.ok(newService.updateNew(dataBaseNew.get()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteNew(Long id) {
        if (newService.existsById(id)) {
            newService.deleteNew(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{id}/image")
    public ResponseEntity<New> updateUserImage(@PathVariable Long id, @RequestBody MultipartFile image) {

        var dataBaseNew = newService.getNewById(id);

        if (dataBaseNew.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (image == null) {
            return ResponseEntity.badRequest().build();
        }

        var uploadedImage = imageUploaderService.uploadImage(dataBaseNew.get().getImage(), image, IMAGES_PATH,
                SERVER_URL, id);

        dataBaseNew.get().setImage(uploadedImage);
        var news = newService.updateNew(dataBaseNew.get());
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("{id}/image")
    public ResponseEntity<New> deleteUserImage(@PathVariable Long id) {

        var dataBaseNew = newService.getNewById(id);

        if (dataBaseNew.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (dataBaseNew.get().getImage() == null) {
            return ResponseEntity.notFound().build();
        }

        dataBaseNew.get().setId(null);

        imageUploaderService.deleteImage(dataBaseNew.get().getImage().getFullPath());

        var updatedNew = newService.updateNew(dataBaseNew.get());

        return ResponseEntity.ok(updatedNew);
    }
}
