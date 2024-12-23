package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface RateMapper {
    Rate toEntity(RateRequest request);

    @Mapping(target = "userId", source = "user.id")
    RateResponse toResponse(Rate rate);
}
