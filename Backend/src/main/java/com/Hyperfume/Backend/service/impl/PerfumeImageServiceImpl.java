package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.ElasticSearch.ESPerfumeService;
import com.Hyperfume.Backend.dto.request.PerfumeImageRequest;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;
import com.Hyperfume.Backend.entity.PerfumeImage;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PerfumeImageMapper;
import com.Hyperfume.Backend.repository.PerfumeImageRepository;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.service.FileService;
import com.Hyperfume.Backend.service.PerfumeImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PerfumeImageServiceImpl implements PerfumeImageService {
    PerfumeImageRepository perfumeImageRepository;
    PerfumeImageMapper perfumeImageMapper;
    PerfumeRepository perfumeRepository;
    ESPerfumeService esPerfumeService;
    FileService fileService;

    private static final int MAX_NORMAL_IMAGE_COUNT = 5;
    private static final String DIRECTORY_KEY = "perfumeImages";

    @PreAuthorize("hasRole('ADMIN')")
    public PerfumeImageResponse addImageThumbnail(PerfumeImageRequest request) {
        perfumeRepository.findById(request.getPerfumeId())
                .orElseThrow(()-> new AppException(ErrorCode.PERFUME_NOT_EXISTED));

        if (perfumeImageRepository.existsByPerfumeIdAndThumbnailTrue(request.getPerfumeId())) {
            throw new AppException(ErrorCode.DUPLICATE_THUMBNAIL_IMAGE);
        }

        MultipartFile imageFile = request.getImageFile();

        String relativePath = fileService.uploadFile(imageFile, DIRECTORY_KEY, "thumbnail");

        PerfumeImage perfumeImage = perfumeImageMapper.toPerfumeImage(request);
        perfumeImage.setImageUrl(relativePath);
        perfumeImage.setThumbnail(true);

        PerfumeImage perfumeImageSaved = perfumeImageRepository.save(perfumeImage);

        esPerfumeService.indexPerfume(perfumeImageSaved.getPerfume());

        return perfumeImageMapper.toResponse(perfumeImageSaved);
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
            String relativePath = fileService.uploadFile(imageFile, DIRECTORY_KEY, "normal");

            PerfumeImage perfumeImage = perfumeImageMapper.toPerfumeImage(request);
            perfumeImage.setImageUrl(relativePath);

            responses.add(perfumeImageMapper.toResponse(perfumeImageRepository.save(perfumeImage)));
        }
        return responses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteImage(Integer imageId) {
        PerfumeImage image = perfumeImageRepository.findById(imageId)
                .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND));

        fileService.deleteFile(image.getImageUrl());

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

        fileService.deleteFile(thumbnail.getImageUrl());

        String relativePath = fileService.uploadFile(request.getImageFile(), DIRECTORY_KEY, "thumbnail");
        thumbnail.setImageUrl(relativePath);

        PerfumeImage perfumeImageSaved = perfumeImageRepository.save(thumbnail);

        esPerfumeService.indexPerfume(perfumeImageSaved.getPerfume());

        return perfumeImageMapper.toResponse(perfumeImageSaved);
    }
}