package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ShippingAddressResponse;

public interface ShippingAddressService {
    ShippingAddressResponse createShippingAddress(ShippingAddressRequest request);

    List<ShippingAddressResponse> getUserShippingAddress();

    ShippingAddressResponse getShippingAddress(Integer shippingAddressId);

    ShippingAddressResponse updateShippingAddress(Integer shippingAddressId, ShippingAddressRequest request);

    void setDefaultShippingAddress(Integer shippingAddressId);

    void deleteShippingAddress(Integer shippingAddressId);
}
