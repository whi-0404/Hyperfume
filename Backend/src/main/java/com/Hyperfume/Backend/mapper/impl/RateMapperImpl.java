package com.Hyperfume.Backend.mapper.impl;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.Rate;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.RateMapper;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RateMapperImpl implements RateMapper {
    PerfumeRepository perfumeRepository;
    public Rate toEntity(RateRequest request) {
        if (request == null) {
            return null;
        } else {
            Rate.RateBuilder rate = Rate.builder();
            rate.rateStar(request.getRateStar());
            rate.rateContext(request.getRateContext());
            rate.perfume(Perfume.builder().id(request.getPerfumeId()).build());
            return rate.build();
        }
    }

    public RateResponse toResponse(Rate rate) {
        if (rate == null) {
            return null;
        } else {
            RateResponse.RateResponseBuilder rateResponse = RateResponse.builder();
            rateResponse.userId(this.rateUserId(rate));
            rateResponse.rateStar(rate.getRateStar());
            rateResponse.rateContext(rate.getRateContext());
            rateResponse.rateDatetime(rate.getRateDatetime());
            rateResponse.userName(rate.getUser().getUsername());
            return rateResponse.build();
        }
    }
    public void updateRate(Rate rate, RateRequest request){
        if(request.getRateContext()!=null){
            rate.setRateContext(request.getRateContext());
        }
        if(request.getRateStar() !=null){
            rate.setRateStar(request.getRateStar());
        }
    }

    private Integer rateUserId(Rate rate) {
        if (rate == null) {
            return null;
        } else {
            User user = rate.getUser();
            if (user == null) {
                return null;
            } else {
                int id = user.getId();
                return id;
            }
        }
    }
}
