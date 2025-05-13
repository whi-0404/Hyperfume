package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.Hyperfume.Backend.dto.request.ScrentFamilyRequest;
import com.Hyperfume.Backend.dto.response.ScrentFamilyResponse;
import com.Hyperfume.Backend.entity.ScrentFamily;

@Mapper(componentModel = "spring")
public interface ScrentFamilyMapper {
    ScrentFamily toScrentFamily(ScrentFamilyRequest request);

    ScrentFamilyResponse toScrentFamilyResponse(ScrentFamily screntFamily);

    void updateScrentFamily(@MappingTarget ScrentFamily screntFamily, ScrentFamilyRequest request);
}
