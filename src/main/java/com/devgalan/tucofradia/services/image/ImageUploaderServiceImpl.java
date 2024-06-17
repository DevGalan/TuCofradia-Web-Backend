package com.devgalan.tucofradia.services.image;

import java.util.Random;

import org.hibernate.mapping.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cloudinary.utils.ObjectUtils;
import com.devgalan.tucofradia.models.ImageModel;
import com.devgalan.tucofradia.models.UploadedImage;
import com.devgalan.tucofradia.repositories.UploadedImageRepository;
import com.devgalan.tucofradia.services.cloudinary.CloudinaryService;

@Service
public class ImageUploaderServiceImpl implements ImageUploaderService {

    private final UploadedImageRepository uploadedImageRepository;

    private final Random random;

    private final CloudinaryService cloudinaryService;

    public ImageUploaderServiceImpl(UploadedImageRepository uploadedImageRepository,
            CloudinaryService cloudinaryService) {
        this.uploadedImageRepository = uploadedImageRepository;
        this.cloudinaryService = cloudinaryService;
        random = new Random();
    }

    @Override
    public UploadedImage uploadImage(UploadedImage uploadedImage, ImageModel imageModel, Long userId) {

        int randomNumber;
        String imageExtension = ".jpg";
        String fileName;
        do {
            randomNumber = random.nextInt();
            fileName = randomNumber + imageExtension;
        } while (uploadedImageRepository.existsByName(fileName));
        imageModel.setName(fileName);

        try {
            if (uploadedImage != null) {
                deleteImage(uploadedImage.getUrl());
            }

            UploadedImage image = new UploadedImage();
            image.setName(imageModel.getName());
            image.setUrl(cloudinaryService.uploadImage(imageModel.getFile(), "folder"));
            image.setUserId(userId);
            if(image.getUrl() == null) {
                return null;
            }
            uploadedImageRepository.save(image);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteImage(String url) {
        cloudinaryService.deleteImage(url);
    }

}
