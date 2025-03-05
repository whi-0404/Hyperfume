package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;

import java.util.List;

public interface RateService {
    RateResponse addRate(RateRequest request);
    List<RateResponse> getRatesByPerfumeId(Integer perfumeId);
}
