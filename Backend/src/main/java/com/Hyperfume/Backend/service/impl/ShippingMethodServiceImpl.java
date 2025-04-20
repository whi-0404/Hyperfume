package com.Hyperfume.Backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.request.ShippingRequest;
import com.Hyperfume.Backend.dto.response.ShippingResponse;
import com.Hyperfume.Backend.entity.ShippingMethod;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.ShippingMethodMapper;
import com.Hyperfume.Backend.repository.ShippingMethodRepository;
import com.Hyperfume.Backend.service.ShippingMethodService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingMethodServiceImpl implements ShippingMethodService {
    ShippingMethodRepository shippingMethodRepository;
    ShippingMethodMapper shippingMethodMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingResponse createShippingMethod(ShippingRequest request) {
        if (shippingMethodRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.SHIPPING_EXISTED);

        ShippingMethod shippingMethod = shippingMethodMapper.toEntity(request);

        return shippingMethodMapper.toResponse(shippingMethodRepository.save(shippingMethod));
    }

    public List<ShippingResponse> getShippingMethods() {

        return shippingMethodRepository.findAll().stream()
                .map(shippingMethodMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingResponse getShippingMethod(Integer shippingId) {
        return shippingMethodMapper.toResponse(shippingMethodRepository
                .findById(shippingId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingResponse updateShippingMethod(Integer shippingId, ShippingRequest request) {
        ShippingMethod shippingMethod = shippingMethodRepository
                .findById(shippingId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_NOT_EXISTED));
        shippingMethodMapper.updateShippingMethod(shippingMethod, request);

        return shippingMethodMapper.toResponse(shippingMethodRepository.save(shippingMethod));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteShippingMethod(Integer shippingId) {
        shippingMethodRepository.deleteById(shippingId);
    }
}
