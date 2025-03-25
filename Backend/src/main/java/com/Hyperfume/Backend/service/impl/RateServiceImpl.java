package com.Hyperfume.Backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Hyperfume.Backend.dto.request.RateRequest;
import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Rate;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.entity.serializable.RateKey;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.RateMapper;
import com.Hyperfume.Backend.repository.PerfumeRepository;
import com.Hyperfume.Backend.repository.RateRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import com.Hyperfume.Backend.service.RateService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RateServiceImpl implements RateService {
    RateRepository rateRepository;
    UserRepository userRepository;
    RateMapper rateMapper;
    PerfumeRepository perfumeRepository;

    @Transactional
    public RateResponse addRate(RateRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!perfumeRepository.existsById(request.getPerfumeId()))
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);

        // Tạo khóa chính cho Rate
        RateKey rateKey = new RateKey(user.getId(), request.getPerfumeId());

        // Kiểm tra nếu đánh giá đã tồn tại
        if (rateRepository.existsById(rateKey)) {
            throw new AppException(ErrorCode.RATE_EXISTED);
        }

        Rate rate = rateMapper.toEntity(request);

        rate.setUser(user);
        rate.setId(rateKey);

        rateRepository.save(rate);
        return rateMapper.toResponse(rate);
    }

    @Transactional(readOnly = true)
    public List<RateResponse> getRatesByPerfumeId(Integer perfumeId) {
        if (!perfumeRepository.existsById(perfumeId)) {
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);
        }

        List<Rate> rates = rateRepository.findByPerfumeId(perfumeId);
        return rates.stream().map(rateMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void updateRate(RateRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!perfumeRepository.existsById(request.getPerfumeId()))
            throw new AppException(ErrorCode.PERFUME_NOT_EXISTED);

        // Tạo khóa chính cho Rate
        RateKey rateKey = new RateKey(user.getId(), request.getPerfumeId());
        Rate rate = rateRepository.findById(rateKey).orElseThrow(() -> new AppException(ErrorCode.RATE_NOT_EXISTED));

        rateMapper.updateRate(rate, request);

        rateRepository.save(rate);
    }
}
