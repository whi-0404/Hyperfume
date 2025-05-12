package com.Hyperfume.Backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String uploadFile(MultipartFile file, String directoryKey, String typeKey);
    List<String> uploadFiles(List<MultipartFile> files, String directoryKey, String typeKey);
    boolean validateFile(MultipartFile file, String directoryKey, String typeKey);
    boolean deleteFile(String relativePath);
}
