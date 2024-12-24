package com.Hyperfume.Backend.mapper.impl;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Rate;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.mapper.RateMapper;
import com.Hyperfume.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RateMapperImpl implements RateMapper {
    private final UserRepository userRepository;

    public Rate toEntity(RateRequest request) {
        if (request == null) {
            return null;
        } else {
            Rate.RateBuilder rate = Rate.builder();
            rate.user(this.rateRequestToUser(request));
            rate.rateStar(request.getRateStar());
            rate.rateContext(request.getRateContext());
            return rate.build();
        }
    }

    public RateResponse toResponse(Rate rate) {
        if (rate == null) {
            return null;
        } else {
            RateResponse.RateResponseBuilder rateResponse = RateResponse.builder();
            rateResponse.userId(this.rateUserId(rate));
            rateResponse.id(rate.getId());
            rateResponse.rateStar(rate.getRateStar());
            rateResponse.rateContext(rate.getRateContext());
            rateResponse.rateDatetime(rate.getRateDatetime());
            rateResponse.userName(rate.getUser().getUsername());
            return rateResponse.build();
        }
    }

    protected User rateRequestToUser(RateRequest rateRequest) {
        if (rateRequest == null) {
            return null;
        } else {
            User.UserBuilder user = User.builder();
            if (rateRequest.getUserId() != null) {
                user.id(rateRequest.getUserId());
            }

            return user.build();
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
