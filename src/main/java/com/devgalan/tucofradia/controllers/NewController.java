package com.devgalan.tucofradia.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devgalan.tucofradia.dtos.user.NoPasswordUserDto;
import com.devgalan.tucofradia.models.New;
import com.devgalan.tucofradia.models.UploadedImage;
import com.devgalan.tucofradia.repositories.UploadedImageRepository;
import com.devgalan.tucofradia.services.image.ImageUploaderService;
import com.devgalan.tucofradia.services.news.NewService;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

        if (dataBaseNew.get().getImage() != null) {
            imageUploaderService.deleteImage(
                    dataBaseNew.get().getImage().getFullPath());
        }

        var random = new Random();
        int randomNumber;
        String filename = image.getOriginalFilename();
        int lastIndexOfDot = filename.lastIndexOf(".");
        String imageExtension = ".jpg";

        if (lastIndexOfDot != -1) {
            imageExtension = filename.substring(lastIndexOfDot);
        }
        do {
            randomNumber = random.nextInt();
        } while (uploadedImageRepository.existsByName(randomNumber + imageExtension));

        UploadedImage uploadedImage = new UploadedImage();
        String imageName = randomNumber + imageExtension;
        uploadedImage.setName(imageName);
        String imagePath = imageUploaderService.uploadImage(image, IMAGES_PATH, imageName);
        uploadedImage.setPath(imagePath);
        uploadedImage.setOnlinePath(SERVER_URL + IMAGES_PATH + imageName);
        uploadedImage.setUserId(dataBaseNew.get().getId());
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
