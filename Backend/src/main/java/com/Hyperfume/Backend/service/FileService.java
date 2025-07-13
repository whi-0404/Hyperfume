package com.Hyperfume.Backend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file, String directoryKey, String typeKey);

    List<String> uploadFiles(List<MultipartFile> files, String directoryKey, String typeKey);

    boolean validateFile(MultipartFile file, String directoryKey, String typeKey);

    boolean deleteFile(String relativePath);
}
