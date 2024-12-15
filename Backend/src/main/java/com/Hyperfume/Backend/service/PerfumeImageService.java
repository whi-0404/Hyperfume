package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.PerfumeImageRequest;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;
import com.Hyperfume.Backend.entity.Perfume;

import com.Hyperfume.Backend.entity.PerfumeImage;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PerfumeImageMapper;
import com.Hyperfume.Backend.repository.PerfumeImageRepository;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PerfumeImageService {
    PerfumeImageRepository perfumeImageRepository;
    PerfumeImageMapper perfumeImageMapper;
    PerfumeRepository perfumeRepository;

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 5MB
    private static final int MAX_NORMAL_IMAGE_COUNT = 5;

    @PreAuthorize("hasRole('ADMIN')")
    public PerfumeImageResponse addImageThumbnail(PerfumeImageRequest request) {
        if (!perfumeRepository.existsById(request.getPerfumeId()))
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        validateImageSize(request.getImageFile());

        byte[] imageData = extractImageData(request.getImageFile());

        PerfumeImage perfumeImage = perfumeImageMapper.toPerfumeImage(request);

        perfumeImage.setThumbnail(true);

        perfumeImage.setImage_data(imageData);

        return perfumeImageMapper.toResponse(perfumeImageRepository.save(perfumeImage));
    }

    public List<PerfumeImageResponse> getImagesByPerfumeId(Integer perfumeId) {
        List<PerfumeImage> images = perfumeImageRepository.findByPerfumeId(perfumeId);
        return images.stream()
                .map(perfumeImageMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PerfumeImageResponse> addMultipleImage(PerfumeImageRequest request) {
        List<PerfumeImage> existingImages = perfumeImageRepository.findByPerfumeId(request.getPerfumeId());

        if (existingImages.size() + request.getImageFiles().size() > MAX_NORMAL_IMAGE_COUNT) {
            throw new AppException(ErrorCode.OUT_OF_PERFUME_IMAGE);
        }

        List<PerfumeImageResponse> responses = new ArrayList<>();

        for (MultipartFile file : request.getImageFiles()) {
            validateImageSize(file);

            byte [] imageData = extractImageData(file);
            PerfumeImage perfumeImage = perfumeImageMapper.toPerfumeImage(request);
            perfumeImage.setImage_data(imageData);

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

    public PerfumeImageResponse getThumbnailByPerfumeId(Integer perfumeId){
        PerfumeImage thumbnail = perfumeImageRepository.findByPerfumeIdAndIsThumbnailTrue(perfumeId)
                .orElseThrow(() -> new AppException(ErrorCode.THUMBNAIL_NOT_FOUND));

        return perfumeImageMapper.toResponse(thumbnail);
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
}

