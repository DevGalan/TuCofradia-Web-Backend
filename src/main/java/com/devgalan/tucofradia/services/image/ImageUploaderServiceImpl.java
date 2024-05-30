package com.devgalan.tucofradia.services.image;

import java.util.Random;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devgalan.tucofradia.data.FileDataAccess;
import com.devgalan.tucofradia.models.UploadedImage;
import com.devgalan.tucofradia.repositories.UploadedImageRepository;

@Service
public class ImageUploaderServiceImpl implements ImageUploaderService {

    private final UploadedImageRepository uploadedImageRepository;

    private final Random random;

    private final FileDataAccess fileDataAccess;

    private final String RESOURCES_PATH = "src/main/resources/static/";

    public ImageUploaderServiceImpl(UploadedImageRepository uploadedImageRepository) {
        this.uploadedImageRepository = uploadedImageRepository;
        this.fileDataAccess = new FileDataAccess(RESOURCES_PATH);
        random = new Random();
    }

    @Override
    public UploadedImage uploadImage(UploadedImage uploadedImage, MultipartFile image, String imagesPath,
            String serverUrl, Long userId) {

        if (uploadedImage != null) {
            fileDataAccess.delete(uploadedImage.getFullPath());
        }

        int randomNumber;
        String filename = image.getOriginalFilename();
        int lastIndexOfDot = filename.lastIndexOf(".");
        String imageExtension = ".jpg";
        String fileName;

        if (lastIndexOfDot != -1) {
            imageExtension = filename.substring(lastIndexOfDot);
        }
        do {
            randomNumber = random.nextInt();
            fileName = randomNumber + imageExtension;
        } while (uploadedImageRepository.existsByName(fileName));

        UploadedImage newUploadedImage = new UploadedImage();
        String imageName = randomNumber + imageExtension;
        String imagePath = fileDataAccess.write(image, imagesPath, imageName);

        newUploadedImage.setName(imageName);
        newUploadedImage.setPath(imagePath);
        newUploadedImage.setOnlinePath(serverUrl + imagesPath + imageName);
        newUploadedImage.setUserId(userId);

        return newUploadedImage;
    }

    @Override
    public void deleteImage(String imagePath) {
        fileDataAccess.delete(imagePath);
    }

    @Override
    public Resource getUploadedImage(String imagePath) {
        return fileDataAccess.read(imagePath);
    }

}
