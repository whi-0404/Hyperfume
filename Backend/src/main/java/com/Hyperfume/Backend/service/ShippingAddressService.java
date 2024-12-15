package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ShippingAddressResponse;
import com.Hyperfume.Backend.entity.ShippingAddress;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.ShippingAddressMapper;
import com.Hyperfume.Backend.repository.ShippingAddressRepository;
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
public class ShippingAddressService {
    ShippingAddressRepository shippingAddressRepository;
    ShippingAddressMapper shippingAddressMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingAddressResponse createShippingAddress(ShippingAddressRequest request){
        ShippingAddress shippingAddress = shippingAddressMapper.toEntity(request);

        return shippingAddressMapper.toResponse(shippingAddressRepository.save(shippingAddress));
    }

    public List<ShippingAddressResponse> getUserShippingAddress(Integer userId)
    {
        return shippingAddressRepository.findByUserId(userId).stream()
                .map(shippingAddressMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingAddressResponse getShippingAddress(Integer shippingAddressId)
    {
        return shippingAddressMapper.toResponse(shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPPING_ADDRESS_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingAddressResponse updateShippingAddress(Integer shippingAddressId, ShippingAddressRequest request)
    {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow(()->new AppException(ErrorCode.SHIPPING_ADDRESS_NOT_EXISTED));
        shippingAddressMapper.updateShippingAddress(shippingAddress, request);

        return  shippingAddressMapper.toResponse(shippingAddressRepository.save(shippingAddress));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteShippingAddress(Integer shippingAddressId)
    {
        shippingAddressRepository.deleteById(shippingAddressId);
    }
}
