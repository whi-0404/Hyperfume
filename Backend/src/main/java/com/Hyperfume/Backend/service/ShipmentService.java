package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.ShipmentRequest;
import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ShipmentResponse;

import java.util.List;

public interface ShipmentService {
    ShipmentResponse getShipmentOrderInfos(ShipmentRequest request);
}
