package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import com.Hyperfume.Backend.entity.Rate;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.RateMapper;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.repository.RateRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class RateService {
    RateRepository rateRepository;
    UserRepository userRepository;
    RateMapper rateMapper;
    PerfumeRepository perfumeRepository;

    @Transactional
    public RateResponse addRate(Integer perfumeId, RateRequest request){
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));


        Rate rate = rateMapper.toEntity(request);

        rate.setPerfume(perfumeRepository.findById(perfumeId)
                .orElseThrow(() ->new AppException(ErrorCode.PERFUME_NOT_EXISTED)));

        rateRepository.save(rateRepository.save(rate));

        return rateMapper.toResponse(rate);
    }

    @Transactional(readOnly = true)
    public List<RateResponse> getRatesByPerfumeId(Integer perfumeId) {
        if(!perfumeRepository.existsById(perfumeId)){
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }

        List<Rate> rates = rateRepository.findByPerfumeId(perfumeId);
        return rates.stream()
                .map(rateMapper::toResponse)
                .collect(Collectors.toList());
    }
}
