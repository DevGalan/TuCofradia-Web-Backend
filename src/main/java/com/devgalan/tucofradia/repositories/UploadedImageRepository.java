package com.devgalan.tucofradia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devgalan.tucofradia.models.UploadedImage;

@Repository
public interface UploadedImageRepository extends JpaRepository<UploadedImage, Long> {

    Boolean existsByName(String name);

}
