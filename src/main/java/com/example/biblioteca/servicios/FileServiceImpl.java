package com.example.biblioteca.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.biblioteca.modelos.FileUploadResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${file.upload-path}")
    private String uploadPath;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file, String subPath) {
        if (file == null) {
            throw new RuntimeException("Fichero nulo");
        }

        try {
            if (!Files.exists(Path.of(uploadPath+"/"+subPath))) {
                Files.createDirectories(Path.of(uploadPath+"/"+subPath));
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + fileExtension;
            String targetPath = this.uploadPath+"/"+subPath + "/" + filename;
            Files.copy(file.getInputStream(), Path.of(targetPath));


            return new FileUploadResponse(filename, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getFile(String filename) {
        try (var file = new FileInputStream(new File(Path.of(uploadPath, filename).toUri()))) {
            return file.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
