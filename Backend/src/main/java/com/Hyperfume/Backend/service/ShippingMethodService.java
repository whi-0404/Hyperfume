package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.ShippingRequest;
import com.Hyperfume.Backend.dto.response.ShippingResponse;

import java.util.List;

public interface ShippingMethodService {
    ShippingResponse createShippingMethod(ShippingRequest request);
    List<ShippingResponse> getShippingMethods();
    ShippingResponse getShippingMethod(Integer shippingId);
    ShippingResponse updateShippingMethod(Integer shippingId, ShippingRequest request);
    void deleteShippingMethod(Integer shippingId);
}
