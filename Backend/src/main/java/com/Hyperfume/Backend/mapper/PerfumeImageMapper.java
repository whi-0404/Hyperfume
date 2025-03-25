package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.PerfumeImageRequest;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;
import com.Hyperfume.Backend.entity.PerfumeImage;

public interface PerfumeImageMapper {

    PerfumeImage toPerfumeImage(PerfumeImageRequest request);

    PerfumeImageResponse toResponse(PerfumeImage perfumeImage);

    //    @Mapping(target = "perfume.id", source = "request.perfumeId")
    //    void updatePerfumeImage(@MappingTarget PerfumeImage perfumeImage, PerfumeImageRequest request);
}
