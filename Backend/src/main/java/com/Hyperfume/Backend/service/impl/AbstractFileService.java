package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.configuration.FileUploadProperties;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFileService implements FileService {
    protected final FileUploadProperties fileUploadProperties;

    @Override
    public String uploadFile(MultipartFile file, String directoryKey, String typeKey) {
        validateFile(file, directoryKey, typeKey);

        try {
            FileUploadProperties.DirectoryConfig dirConfig = fileUploadProperties.getDirectoryConfig(directoryKey);

            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : getDefaultExtension(file.getContentType());

            String uniqueFilename = generateUniqueFilename(fileExtension);

            String subDirectory = dirConfig.getPath() + "/" + typeKey;
            Path directoryPath = Paths.get(fileUploadProperties.getRootDirectory(), subDirectory);

            ensureDirectoryExists(directoryPath);

            Path filePath = directoryPath.resolve(uniqueFilename);
            file.transferTo(filePath.toFile());

            return subDirectory + "/" + uniqueFilename;
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> files, String directoryKey, String typeKey) {
        List<String> filePaths = new ArrayList<>();

        for (MultipartFile file : files) {
            String path = uploadFile(file, directoryKey, typeKey);
            filePaths.add(path);
        }

        return filePaths;
    }

    @Override
    public boolean validateFile(MultipartFile file, String directoryKey, String typeKey) {
        if (file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }

        FileUploadProperties.DirectoryConfig dirConfig = fileUploadProperties.getDirectoryConfig(directoryKey);

        long maxFileSize = dirConfig.getMaxFileSize() != null ?
                dirConfig.getMaxFileSize() : fileUploadProperties.getMaxFileSize();

        if (file.getSize() > maxFileSize) {
            throw new AppException(ErrorCode.FILE_TOO_LARGE);
        }

        String contentType = file.getContentType();
        if (contentType != null && dirConfig.getAllowedMimeTypes().containsKey(typeKey)) {
            String[] allowedMimeTypes = dirConfig.getAllowedMimeTypes().get(typeKey);
            if (!Arrays.asList(allowedMimeTypes).contains(contentType) &&
                    !Arrays.stream(allowedMimeTypes).anyMatch(contentType::startsWith)) {
                throw new AppException(ErrorCode.INVALID_FILE_TYPE);
            }
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && dirConfig.getAllowedExtensions().containsKey(typeKey)) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            String[] allowedExtensions = dirConfig.getAllowedExtensions().get(typeKey);
            if (!Arrays.asList(allowedExtensions).contains(fileExtension)) {
                throw new AppException(ErrorCode.INVALID_FILE_TYPE);
            }
        }

        return true;
    }

    @Override
    public boolean deleteFile(String relativePath) {
        if (!StringUtils.hasText(relativePath)) {
            return false;
        }

        try {
            Path filePath = Paths.get(fileUploadProperties.getRootDirectory(), relativePath);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Không thể xóa file: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Tạo tên file duy nhất với UUID
     */
    protected String generateUniqueFilename(String fileExtension) {
        return UUID.randomUUID() + fileExtension;
    }

    /**
     * Đảm bảo thư mục tồn tại, nếu không thì tạo mới
     */
    protected void ensureDirectoryExists(Path directoryPath) {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new AppException(ErrorCode.DIRECTORY_CREATION_FAILED);
            }
        }
    }

    /**
     * Lấy phần mở rộng mặc định dựa vào MIME type
     */
    protected String getDefaultExtension(String contentType) {
        if (contentType == null) {
            return ".bin";
        }

        return switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/gif" -> ".gif";
            case "video/mp4" -> ".mp4";
            case "video/webm" -> ".webm";
            default -> ".bin";
        };
    }
}
