package com.Hyperfume.Backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.request.PaymentMethodRequest;
import com.Hyperfume.Backend.dto.response.PaymentMethodResponse;
import com.Hyperfume.Backend.entity.PaymentMethod;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.PaymentMethodMapper;
import com.Hyperfume.Backend.repository.PaymentMethodRepository;
import com.Hyperfume.Backend.service.PaymentMethodService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodServiceImpl implements PaymentMethodService {
    PaymentMethodRepository paymentMethodRepository;
    PaymentMethodMapper paymentMethodMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PaymentMethodResponse createPaymentMethod(PaymentMethodRequest request) {
        if (paymentMethodRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.PAYMENT_METHOD_EXISTED);

        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(request);

        return paymentMethodMapper.toResponse(paymentMethodRepository.save(paymentMethod));
    }

    public List<PaymentMethodResponse> getPaymentMethods() {

        return paymentMethodRepository.findAll().stream()
                .map(paymentMethodMapper::toResponse)
                .collect(Collectors.toList());
    }

    public PaymentMethodResponse getPaymentMethod(Integer paymentMethodId) {
        return paymentMethodMapper.toResponse(paymentMethodRepository
                .findById(paymentMethodId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PaymentMethodResponse updatePaymentMethod(Integer paymentMethodId, PaymentMethodRequest request) {
        PaymentMethod paymentMethod = paymentMethodRepository
                .findById(paymentMethodId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_EXISTED));
        paymentMethodMapper.updatePaymentMethod(paymentMethod, request);

        return paymentMethodMapper.toResponse(paymentMethodRepository.save(paymentMethod));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletePaymentMethod(Integer paymentMethodId) {
        paymentMethodRepository.deleteById(paymentMethodId);
    }
}
