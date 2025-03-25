package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.ScrentFamilyRequest;
import com.Hyperfume.Backend.dto.response.ScrentFamilyResponse;

public interface ScrentFamilyService {
    ScrentFamilyResponse createScrentFamily(ScrentFamilyRequest request);

    List<ScrentFamilyResponse> getScrentFamilies();

    ScrentFamilyResponse getScrentFamily(Integer screntFamilyId);

    ScrentFamilyResponse updateScrentFamily(Integer screntFamilyId, ScrentFamilyRequest request);

    void deleteScrentFamily(Integer screntFamilyId);
}
