package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.PerfumeImageRequest;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;

import java.util.List;

public interface PerfumeImageService {
    PerfumeImageResponse addImageThumbnail(PerfumeImageRequest request);
    List<PerfumeImageResponse> getImagesByPerfumeId(Integer perfumeId);
    List<PerfumeImageResponse> addMultipleImage(PerfumeImageRequest request);
//    void deleteImage(Integer imageId);
    PerfumeImageResponse getThumbnailByPerfumeId(Integer perfumeId);
    PerfumeImageResponse updateThumbnailByPerfumeId(PerfumeImageRequest request);
}
