package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.PerfumeRequest;
import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;
import com.Hyperfume.Backend.entity.Brand;
import com.Hyperfume.Backend.entity.Country;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.ScrentFamily;
import org.mapstruct.MappingTarget;

public interface PerfumeMapper {

    PerfumeResponse toResponse(Perfume perfume);

    Perfume toEntity(PerfumeRequest request, Brand brand, ScrentFamily screntFamily, Country country);

    void updateEntity(@MappingTarget Perfume perfume, PerfumeRequest request, Brand brand, ScrentFamily screntFamily, Country country);

    PerfumeGetAllResponse toGetAllPerfumeResponse(Perfume perfume);
}
