package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.models.ProfilePhoto;
import com.mf.minutefictionbackend.repositories.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class PhotoService {

    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final FileUploadRepository fileUploadRepository;

    public PhotoService(@Value("${my.upload_location}") String fileStorageLocation, FileUploadRepository fileUploadRepository) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.fileUploadRepository = fileUploadRepository;

        Files.createDirectories(fileStoragePath);
    }

    public String storeFile(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + File.separator + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        fileUploadRepository.save(new ProfilePhoto(fileName));
        return fileName;
    }

    public Resource downLoadFile(String fileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("The file doesn't exist or isn't readable");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }
        return resource;
    }

    public void deleteFile(String fileName) {
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file " + fileName, e);
        }
    }
}
