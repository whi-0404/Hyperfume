package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.PaymentMethodRequest;
import com.Hyperfume.Backend.dto.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    PaymentMethodResponse createPaymentMethod(PaymentMethodRequest request);
    List<PaymentMethodResponse> getPaymentMethods();
    PaymentMethodResponse getPaymentMethod(Integer paymentMethodId);
    PaymentMethodResponse updatePaymentMethod(Integer paymentMethodId, PaymentMethodRequest request);
    void deletePaymentMethod(Integer paymentMethodId);

}
