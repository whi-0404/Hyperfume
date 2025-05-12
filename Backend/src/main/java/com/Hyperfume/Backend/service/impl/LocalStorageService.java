//package com.Hyperfume.Backend.service.impl;
//
//import com.Hyperfume.Backend.exception.AppException;
//import com.Hyperfume.Backend.exception.ErrorCode;
//import com.Hyperfume.Backend.service.StorageService;
//import jakarta.annotation.PostConstruct;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.experimental.NonFinal;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class LocalStorageService implements StorageService {
//
//    @Value("${file.upload.root-dir}")
//    String rootDirectory;
//
//    @Value("${file.access.url-prefix}")
//    String accessUrlPrefix;
//
//    Path rootLocation;
//
//    @PostConstruct
//    @Override
//    public void init() throws IOException {
//        this.rootLocation = Paths.get(rootDirectory).toAbsolutePath().normalize();
//        Files.createDirectories(rootLocation);
//
//    }
//
//    @Override
//    public String store(MultipartFile file, String targetDir) {
//        try {
//            if (file.isEmpty()) {
//                throw new AppException(ErrorCode.FILE_EMPTY);
//            }
//
//            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//
//            // Tạo tên tệp tin duy nhất
//            String fileExtension = getFileExtension(originalFilename);
//            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
//
//            // Đảm bảo thư mục đích tồn tại
//            Path targetPath = getTargetPath(targetDir);
//            Files.createDirectories(targetPath);
//
//            // Sao chép tệp vào vị trí lưu trữ
//            Path destinationFile = targetPath.resolve(uniqueFilename).normalize();
//            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
//
//            // Trả về đường dẫn tương đối
//            String relativePath = getRelativePath(destinationFile);
//
//            return relativePath;
//
//        } catch (IOException e) {
//            throw new AppException(ErrorCode.FILE_FAILED_STORED);
//        }
//    }
//
//    @Override
//    public Optional<Resource> loadAsResource(String relativePath) {
//        try {
//            Path filePath = getAbsolutePath(relativePath);
//
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists() && resource.isReadable()) {
//                return Optional.of(resource);
//            } else {
//                return Optional.empty();
//            }
//        } catch (MalformedURLException e) {
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public boolean delete(String relativePath) {
//        try {
//            Path filePath = getAbsolutePath(relativePath);
//            return Files.deleteIfExists(filePath);
//        } catch (IOException e) {
//            log.error("Không thể xóa tệp tin: {}", relativePath, e);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean exists(String relativePath) {
//        Path filePath = getAbsolutePath(relativePath);
//        return Files.exists(filePath);
//    }
//
//    @Override
//    public Path getAbsolutePath(String relativePath) {
//        // Chuẩn hóa đường dẫn tương đối, đảm bảo không bắt đầu bằng /
//        String cleanPath = relativePath.replaceAll("^/+", "");
//
//        return rootLocation.resolve(cleanPath).normalize();
//    }
//
//    @Override
//    public String getRelativePath(Path absolutePath) {
//        try {
//            // Lấy đường dẫn tương đối từ thư mục gốc
//            Path relativePath = rootLocation.relativize(absolutePath.normalize());
//
//            // Sử dụng '/' cho đường dẫn tương đối (chuẩn web)
//            return relativePath.toString().replace('\\', '/');
//        } catch (IllegalArgumentException e) {
//            throw new AppException(ErrorCode.FILE_FAILED_GET_RELATIVE_PATH);
//        }
//    }
//
//    @Override
//    public String getAccessUrl(String relativePath) {
//        if (relativePath == null || relativePath.isEmpty()) {
//            return null;
//        }
//
//        // Chuẩn hóa đường dẫn tương đối, đảm bảo không bắt đầu bằng /
//        String cleanPath = relativePath.replaceAll("^/+", "");
//
//        // Tạo URL truy cập
//        return accessUrlPrefix + "/" + cleanPath;
//    }
//
//    @Override
//    public String copy(String sourcePath, String targetDir) {
//        try {
//            // Lấy đường dẫn tuyệt đối của tệp nguồn
//            Path sourceFile = getAbsolutePath(sourcePath);
//            if (!Files.exists(sourceFile)) {
//                throw new AppException(ErrorCode.FILE_NOT_EXISTED_SOURCE_FILE);
//            }
//
//            // Đảm bảo thư mục đích tồn tại
//            Path targetPath = getTargetPath(targetDir);
//            Files.createDirectories(targetPath);
//
//            // Tạo tên tệp tin duy nhất
//            String originalFilename = sourceFile.getFileName().toString();
//            String fileExtension = getFileExtension(originalFilename);
//            String uniqueFilename = UUID.randomUUID() + fileExtension;
//
//            // Sao chép tệp
//            Path destinationFile = targetPath.resolve(uniqueFilename).normalize();
//            Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
//
//            // Trả về đường dẫn tương đối
//            return getRelativePath(destinationFile);
//
//        } catch (IOException e) {
//            throw new AppException(ErrorCode.FILE_FAILED_COPY);
//        }
//    }
//
//    private String getFileExtension(String filename) {
//        if (filename == null || !filename.contains(".")) {
//            return "";
//        }
//        return filename.substring(filename.lastIndexOf("."));
//    }
//
//    private Path getTargetPath(String targetDir) {
//        if (targetDir == null || targetDir.isEmpty()) {
//            return rootLocation;
//        }
//
//        // Chuẩn hóa đường dẫn thư mục đích, đảm bảo không bắt đầu bằng /
//        String cleanPath = targetDir.replaceAll("^/+", "");
//
//        return rootLocation.resolve(cleanPath).normalize();
//    }
//}
