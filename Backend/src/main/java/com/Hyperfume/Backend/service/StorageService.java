package com.Hyperfume.Backend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface StorageService {
    void init() throws IOException;
    String store(MultipartFile file, String targetDir) throws IOException;
    Optional<Resource> loadAsResource(String relativePath);
    boolean delete(String relativePath);
    boolean exists(String relativePath);
    Path getAbsolutePath(String relativePath);
    String getRelativePath(Path absolutePath);
    String getAccessUrl(String relativePath);
    String copy(String sourcePath, String targetDir);
}
