package com.Hyperfume.Backend.mapper.impl;

import org.springframework.stereotype.Component;

import com.Hyperfume.Backend.dto.request.PerfumeImageRequest;
import com.Hyperfume.Backend.dto.response.PerfumeImageResponse;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.PerfumeImage;
import com.Hyperfume.Backend.mapper.PerfumeImageMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PerfumeImageMapperImpl implements PerfumeImageMapper {

    public PerfumeImage toPerfumeImage(PerfumeImageRequest request) {
        if (request == null) {
            return null;
        } else {
            PerfumeImage.PerfumeImageBuilder perfumeImage = PerfumeImage.builder();
            perfumeImage.perfume(this.perfumeImageRequestToPerfume(request));
            return perfumeImage.build();
        }
    }

    public PerfumeImageResponse toResponse(PerfumeImage perfumeImage) {
        if (perfumeImage == null) {
            return null;
        } else {
            PerfumeImageResponse.PerfumeImageResponseBuilder perfumeImageResponse = PerfumeImageResponse.builder();
            perfumeImageResponse.perfumeId(this.perfumeImagePerfumeId(perfumeImage));
            perfumeImageResponse.id(perfumeImage.getId());
            perfumeImageResponse.thumbnail(perfumeImage.isThumbnail());
            perfumeImageResponse.imageUrl(perfumeImage.getImageUrl());
            return perfumeImageResponse.build();
        }
    }

    protected Perfume perfumeImageRequestToPerfume(PerfumeImageRequest perfumeImageRequest) {
        if (perfumeImageRequest == null) {
            return null;
        } else {
            Perfume.PerfumeBuilder perfume = Perfume.builder();
            perfume.id(perfumeImageRequest.getPerfumeId());
            return perfume.build();
        }
    }

    private int perfumeImagePerfumeId(PerfumeImage perfumeImage) {
        if (perfumeImage == null) {
            return 0;
        } else {
            Perfume perfume = perfumeImage.getPerfume();
            if (perfume == null) {
                return 0;
            } else {
                int id = perfume.getId();
                return id;
            }
        }
    }
}
