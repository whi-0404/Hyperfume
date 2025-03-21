package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.PerfumeRequest;
import com.Hyperfume.Backend.dto.response.PageResponse;
import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.dto.response.PerfumeResponse;

import java.util.List;

public interface PerfumeService {
    PageResponse<PerfumeGetAllResponse> getAllPerfumes(int page, int size, String sortOption, String gender,
                                                       String longevity, Integer countryId, Integer brandId, String concentration,
                                                       Integer screntFamilyId, Long maxPrice);
    PerfumeResponse getPerfumeById(int id);
    PerfumeResponse createPerfume(PerfumeRequest request);
    PerfumeResponse updatePerfume(int id, PerfumeRequest request);
    void deletePerfume(int id);
    void toggleFlashSale(Integer perfumeId, boolean isFlashSale);
    PageResponse<PerfumeGetAllResponse> getFlashSalePerfumes(int page, int size);
    PageResponse<PerfumeGetAllResponse> getPerfumesByTypeName(String typeName, int page, int size);
    PageResponse<PerfumeGetAllResponse> getPerfumesByGender(String gender, int page, int size);
    PageResponse<PerfumeGetAllResponse> searchPerfumesByName(String name, int page, int size);
    PageResponse<PerfumeGetAllResponse> getPerfumesByCountry(Integer countryId, int page, int size);
    PageResponse<PerfumeGetAllResponse> getPerfumesByBrand(Integer brandId, int page, int size);
    PageResponse<PerfumeGetAllResponse> getPerfumesByScrentFamily(Integer screntFamily, int page, int size);
}
