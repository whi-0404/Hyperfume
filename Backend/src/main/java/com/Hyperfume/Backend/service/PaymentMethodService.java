package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.PaymentMethodRequest;
import com.Hyperfume.Backend.dto.response.PaymentMethodResponse;

public interface PaymentMethodService {
    PaymentMethodResponse createPaymentMethod(PaymentMethodRequest request);

    List<PaymentMethodResponse> getPaymentMethods();

    PaymentMethodResponse getPaymentMethod(Integer paymentMethodId);

    PaymentMethodResponse updatePaymentMethod(Integer paymentMethodId, PaymentMethodRequest request);

    void deletePaymentMethod(Integer paymentMethodId);
}
