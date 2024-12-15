package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RateMapper {
    @Mapping(target = "perfume.id", source = "perfumeId")
    @Mapping(target = "user.id", source = "userId")
    Rate toEntity(RateRequest request);

    @Mapping(target = "perfumeId", source = "perfume.id")
    @Mapping(target = "userId", source = "user.id")
    RateResponse toResponse(Rate rate);
}
