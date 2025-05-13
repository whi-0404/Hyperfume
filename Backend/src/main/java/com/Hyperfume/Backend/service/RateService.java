package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;

public interface RateService {
    RateResponse addRate(RateRequest request);

    List<RateResponse> getRatesByPerfumeId(Integer perfumeId);

    void updateRate(RateRequest request);
}
