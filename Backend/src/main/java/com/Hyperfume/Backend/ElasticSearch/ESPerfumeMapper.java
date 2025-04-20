package com.Hyperfume.Backend.ElasticSearch;

import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.entity.Perfume;

public interface ESPerfumeMapper {
    ESPerfume toDocument(Perfume perfume);

    PerfumeGetAllResponse toGetAllResponse(ESPerfume esPerfume);
}
