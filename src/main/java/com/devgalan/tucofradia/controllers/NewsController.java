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

import com.devgalan.tucofradia.dtos.news.CreateNewsDto;
import com.devgalan.tucofradia.dtos.news.ViewNewsDto;
import com.devgalan.tucofradia.mappers.news.CreateNewsMapper;
import com.devgalan.tucofradia.mappers.news.ViewNewsMapper;
import com.devgalan.tucofradia.services.image.ImageUploaderService;
import com.devgalan.tucofradia.services.news.NewService;

@RestController
@RequestMapping("api/news")
public class NewsController {

    private final NewService newService;

    private final ImageUploaderService imageUploaderService;

    private final ViewNewsMapper viewNewsMapper;

    private final CreateNewsMapper createNewsMapper;

    private final String IMAGES_PATH = "uploaded_images/images_of_news/";

    @Value("${server.url}")
    private String SERVER_URL;

    public NewsController(NewService newService, ImageUploaderService imageUploaderService,
            ViewNewsMapper viewNewsMapper, CreateNewsMapper createNewsMapper) {
        this.newService = newService;
        this.imageUploaderService = imageUploaderService;
        this.viewNewsMapper = viewNewsMapper;
        this.createNewsMapper = createNewsMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<ViewNewsDto>> getNews() {
        return ResponseEntity.ok(viewNewsMapper.toDto(newService.getNews()));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNew(@RequestBody CreateNewsDto createNew) {
        newService.createNew(createNewsMapper.toEntity(createNew));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<ViewNewsDto> updateNew(@PathVariable Long id, CreateNewsDto updateNew) {
        var dataBaseNew = newService.getNewById(id);
        if (dataBaseNew.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        dataBaseNew.get().setTitle(updateNew.getTitle());
        dataBaseNew.get().setBody(updateNew.getBody());
        dataBaseNew.get().setDate(updateNew.getDate());
        var updatedNew = newService.updateNew(dataBaseNew.get());
        return ResponseEntity.ok(viewNewsMapper.toDto(updatedNew));
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
    public ResponseEntity<ViewNewsDto> updateUserImage(@PathVariable Long id, @RequestBody MultipartFile image) {

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
        return ResponseEntity.ok(viewNewsMapper.toDto(news));
    }

    @DeleteMapping("{id}/image")
    public ResponseEntity<ViewNewsDto> deleteUserImage(@PathVariable Long id) {

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

        return ResponseEntity.ok(viewNewsMapper.toDto(updatedNew));
    }
}
