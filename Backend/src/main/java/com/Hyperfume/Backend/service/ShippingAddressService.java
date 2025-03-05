package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ShippingAddressResponse;

import java.util.List;

public interface ShippingAddressService {
    ShippingAddressResponse createShippingAddress(ShippingAddressRequest request);
    List<ShippingAddressResponse> getUserShippingAddress();
    ShippingAddressResponse getShippingAddress(Integer shippingAddressId);
    ShippingAddressResponse updateShippingAddress(Integer shippingAddressId, ShippingAddressRequest request);
    void deleteShippingAddress(Integer shippingAddressId);
}
