package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.PerfumeImageRequest;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;

public interface PerfumeImageService {
    PerfumeImageResponse addImageThumbnail(PerfumeImageRequest request);

    List<PerfumeImageResponse> getImagesByPerfumeId(Integer perfumeId);

    List<PerfumeImageResponse> addMultipleImage(PerfumeImageRequest request);
    //    void deleteImage(Integer imageId);
    PerfumeImageResponse getThumbnailByPerfumeId(Integer perfumeId);

    PerfumeImageResponse updateThumbnailByPerfumeId(PerfumeImageRequest request);
}
