package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapping;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Rate;

public interface RateMapper {
    Rate toEntity(RateRequest request);

    @Mapping(target = "userId", source = "user.id")
    RateResponse toResponse(Rate rate);

    void updateRate(Rate rate, RateRequest request);
}
