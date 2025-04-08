package com.Hyperfume.Backend.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Hyperfume.Backend.dto.request.PerfumeImageRequest;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;
import com.Hyperfume.Backend.entity.PerfumeImage;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PerfumeImageMapper;
import com.Hyperfume.Backend.repository.PerfumeImageRepository;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.service.PerfumeImageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PerfumeImageServiceImpl implements PerfumeImageService {
    PerfumeImageRepository perfumeImageRepository;
    PerfumeImageMapper perfumeImageMapper;
    PerfumeRepository perfumeRepository;

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 5MB
    private static final int MAX_NORMAL_IMAGE_COUNT = 5;

    private static final String UP_LOAD_DIR =
            "E:\\Final_Hyperfume\\Hyperfume\\Frontend\\src\\assets\\productImages\\images";

    @PreAuthorize("hasRole('ADMIN')")
    public PerfumeImageResponse addImageThumbnail(PerfumeImageRequest request) {
        if (!perfumeRepository.existsById(request.getPerfumeId()))
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);

        if (perfumeImageRepository.existsByPerfumeIdAndThumbnailTrue(request.getPerfumeId())) {
            throw new AppException(ErrorCode.DUPLICATE_THUMBNAIL_IMAGE);
        }

        validateImageSize(request.getImageFile());

        MultipartFile imageFile = request.getImageFile();

        PerfumeImage perfumeImage = perfumeImageMapper.toPerfumeImage(request);

        String uniqueFileName = uploadImageFileToDir(imageFile);

        // Set the relative path (URL) in the object
        String relativePath = "images/" + uniqueFileName;
        perfumeImage.setImageUrl(relativePath);
        perfumeImage.setThumbnail(true);

        return perfumeImageMapper.toResponse(perfumeImageRepository.save(perfumeImage));
    }

    public List<PerfumeImageResponse> getImagesByPerfumeId(Integer perfumeId) {
        List<PerfumeImage> images = perfumeImageRepository.findByPerfumeId(perfumeId);
        return images.stream().map(perfumeImageMapper::toResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PerfumeImageResponse> addMultipleImage(PerfumeImageRequest request) {
        List<PerfumeImage> existingImages = perfumeImageRepository.findByPerfumeId(request.getPerfumeId());

        if (existingImages.size() + request.getImageFiles().size() > MAX_NORMAL_IMAGE_COUNT) {
            throw new AppException(ErrorCode.OUT_OF_PERFUME_IMAGE);
        }

        List<PerfumeImageResponse> responses = new ArrayList<>();

        for (MultipartFile imageFile : request.getImageFiles()) {
            validateImageSize(imageFile);

            String uniqueFileName = uploadImageFileToDir(imageFile);

            // Set the relative path (URL) in the object
            String relativePath = "images/" + uniqueFileName;

            PerfumeImage perfumeImage = perfumeImageMapper.toPerfumeImage(request);
            perfumeImage.setImageUrl(relativePath);

            responses.add(perfumeImageMapper.toResponse(perfumeImageRepository.save(perfumeImage)));
        }
        return responses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteImage(Integer imageId) {
        if (!perfumeImageRepository.existsById(imageId)) {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
        perfumeImageRepository.deleteById(imageId);
    }

    public PerfumeImageResponse getThumbnailByPerfumeId(Integer perfumeId) {
        PerfumeImage thumbnail = perfumeImageRepository
                .findByPerfumeIdAndIsThumbnailTrue(perfumeId)
                .orElseThrow(() -> new AppException(ErrorCode.THUMBNAIL_NOT_FOUND));

        return perfumeImageMapper.toResponse(thumbnail);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PerfumeImageResponse updateThumbnailByPerfumeId(PerfumeImageRequest request) {
        PerfumeImage thumbnail = perfumeImageRepository
                .findByPerfumeIdAndIsThumbnailTrue(request.getPerfumeId())
                .orElseThrow(() -> new AppException(ErrorCode.THUMBNAIL_NOT_FOUND));

        MultipartFile imageFile = request.getImageFile();
        validateImageSize(imageFile);

        String uniqueFileName = uploadImageFileToDir(imageFile);

        // Set the relative path (URL) in the object
        String relativePath = "images/" + uniqueFileName;
        thumbnail.setImageUrl(relativePath);

        return perfumeImageMapper.toResponse(perfumeImageRepository.save(thumbnail));
    }

    private void validateImageSize(MultipartFile file) {
        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new AppException(ErrorCode.INVALID_IMAGE_FILE);
        }
    }

    private byte[] extractImageData(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new AppException(ErrorCode.FAILED_READ_IMAGE);
        }
    }

    private String uploadImageFileToDir(MultipartFile imageFile) {
        try {
            String originalFileName = imageFile.getOriginalFilename();
            String fileExtension =
                    originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : ".jpg";
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            Path filePath = Paths.get(UP_LOAD_DIR, uniqueFileName);

            imageFile.transferTo(filePath.toFile());

            return uniqueFileName;
        } catch (IOException e) {
            throw new AppException(ErrorCode.FAILED_READ_IMAGE);
        }
    }
}
