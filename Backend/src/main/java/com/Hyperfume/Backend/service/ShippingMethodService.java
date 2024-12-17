package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.ShippingRequest;
import com.Hyperfume.Backend.dto.response.ShippingResponse;
import com.Hyperfume.Backend.entity.ShippingMethod;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.ShippingMethodMapper;
import com.Hyperfume.Backend.repository.ShippingMethodRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class ShippingMethodService {
    ShippingMethodRepository shippingMethodRepository;
    ShippingMethodMapper shippingMethodMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingResponse createShippingMethod(ShippingRequest request){
        if(shippingMethodRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.SHIPPING_EXISTED);

        ShippingMethod shippingMethod = shippingMethodMapper.toEntity(request);

        return shippingMethodMapper.toResponse(shippingMethodRepository.save(shippingMethod));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ShippingResponse> getShippingMethods()
    {

        return shippingMethodRepository.findAll().stream()
                .map(shippingMethodMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingResponse getShippingMethod(Integer shippingId)
    {
        return shippingMethodMapper.toResponse(shippingMethodRepository.findById(shippingId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingResponse updateShippingMethod(Integer shippingId, ShippingRequest request)
    {
        ShippingMethod shippingMethod = shippingMethodRepository.findById(shippingId)
                .orElseThrow(()->new AppException(ErrorCode.SHIPPING_NOT_EXISTED));
        shippingMethodMapper.updateShippingMethod(shippingMethod, request);

        return  shippingMethodMapper.toResponse(shippingMethodRepository.save(shippingMethod));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteShippingMethod(Integer shippingId)
    {
        shippingMethodRepository.deleteById(shippingId);
    }
}
