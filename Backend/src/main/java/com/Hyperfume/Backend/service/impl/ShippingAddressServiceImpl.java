package com.Hyperfume.Backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ShippingAddressResponse;
import com.Hyperfume.Backend.entity.ShippingAddress;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.ShippingAddressMapper;
import com.Hyperfume.Backend.repository.ShippingAddressRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import com.Hyperfume.Backend.service.ShippingAddressService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingAddressServiceImpl implements ShippingAddressService {
    ShippingAddressRepository shippingAddressRepository;
    ShippingAddressMapper shippingAddressMapper;
    UserRepository userRepository;

    public ShippingAddressResponse createShippingAddress(ShippingAddressRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        ShippingAddress shippingAddress = shippingAddressMapper.toEntity(request);
        shippingAddress.setUser(user);

        return shippingAddressMapper.toResponse(shippingAddressRepository.save(shippingAddress));
    }

    public List<ShippingAddressResponse> getUserShippingAddress() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return shippingAddressRepository.findByUserId(user.getId()).stream()
                .map(shippingAddressMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingAddressResponse getShippingAddress(Integer shippingAddressId) {
        return shippingAddressMapper.toResponse(shippingAddressRepository
                .findById(shippingAddressId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_ADDRESS_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ShippingAddressResponse updateShippingAddress(Integer shippingAddressId, ShippingAddressRequest request) {
        ShippingAddress shippingAddress = shippingAddressRepository
                .findById(shippingAddressId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_ADDRESS_NOT_EXISTED));
        shippingAddressMapper.updateShippingAddress(shippingAddress, request);

        return shippingAddressMapper.toResponse(shippingAddressRepository.save(shippingAddress));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteShippingAddress(Integer shippingAddressId) {
        shippingAddressRepository.deleteById(shippingAddressId);
    }
}
