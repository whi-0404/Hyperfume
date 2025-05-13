package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.ShipmentRequest;
import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.Hyperfume.Backend.entity.Order;
import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.enums.ShipmentStatus;

import java.util.List;

public interface ShipmentService {

    ShipmentResponse getShipmentOrderInfo(int shippingAddressId, int quantity);

    Shipment createShipment(Order order, String shipmentToken);

    Shipment updateShipmentStatus(Shipment shipment, ShipmentStatus status, String location, String description);
}
