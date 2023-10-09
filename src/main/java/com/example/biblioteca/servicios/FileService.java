package com.example.biblioteca.servicios;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    com.example.biblioteca.modelos.FileUploadResponse uploadFile(MultipartFile file, String subpath);

    byte[] getFile(String filename);
}
