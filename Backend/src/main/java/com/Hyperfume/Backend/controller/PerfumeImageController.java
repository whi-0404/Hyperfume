package com.Hyperfume.Backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.PerfumeImageRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;
import com.Hyperfume.Backend.service.PerfumeImageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/perfumes/{perfumeId}/images")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PerfumeImageController {
    PerfumeImageService perfumeImageService;

    @GetMapping
    public ApiResponse<List<PerfumeImageResponse>> getImages(@PathVariable Integer perfumeId) {
        return ApiResponse.<List<PerfumeImageResponse>>builder()
                .result(perfumeImageService.getImagesByPerfumeId(perfumeId))
                .build();
    }

    @PostMapping("/thumbnail")
    public ApiResponse<PerfumeImageResponse> addImageThumbnail(@ModelAttribute @Valid PerfumeImageRequest request) {
        return ApiResponse.<PerfumeImageResponse>builder()
                .result(perfumeImageService.addImageThumbnail(request))
                .build();
    }

    @PutMapping("/thumbnail")
    public ApiResponse<PerfumeImageResponse> updateImageThumbnail(@ModelAttribute @Valid PerfumeImageRequest request) {
        return ApiResponse.<PerfumeImageResponse>builder()
                .result(perfumeImageService.updateThumbnailByPerfumeId(request))
                .build();
    }

    @PostMapping("/multiple")
    public ApiResponse<List<PerfumeImageResponse>> addMultipleImages(
            @ModelAttribute @Valid PerfumeImageRequest request) {
        return ApiResponse.<List<PerfumeImageResponse>>builder()
                .result(perfumeImageService.addMultipleImage(request))
                .build();
    }

    @GetMapping("/thumbnail")
    public ApiResponse<PerfumeImageResponse> getThumbnail(@PathVariable Integer perfumeId) {
        return ApiResponse.<PerfumeImageResponse>builder()
                .result(perfumeImageService.getThumbnailByPerfumeId(perfumeId))
                .build();
    }

    //    @DeleteMapping("/{imageId}")
    //    public ApiResponse<String> deleteImage (@PathVariable Integer imageId){
    //        perfumeImageService.deleteImage(imageId);
    //
    //        return ApiResponse.<String> builder()
    //                .result("Image has been deleted")
    //                .build();
    //    }
}
