package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.ScrentFamilyRequest;
import com.Hyperfume.Backend.dto.response.ScrentFamilyResponse;
import com.Hyperfume.Backend.entity.ScrentFamily;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScrentFamilyMapper {
    ScrentFamily toScrentFamily(ScrentFamilyRequest request);

    ScrentFamilyResponse toScrentFamilyResponse(ScrentFamily screntFamily);

    void updateScrentFamily(@MappingTarget ScrentFamily screntFamily, ScrentFamilyRequest request);
}
