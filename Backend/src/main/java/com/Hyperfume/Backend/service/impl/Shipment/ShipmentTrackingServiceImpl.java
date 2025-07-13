package com.Hyperfume.Backend.service.impl.Shipment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.entity.ShipmentTracking;
import com.Hyperfume.Backend.repository.ShipmentTrackingRepository;
import com.Hyperfume.Backend.service.ShipmentTrackingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentTrackingServiceImpl implements ShipmentTrackingService {
    ShipmentTrackingRepository shipmentTrackingRepository;

    public ShipmentTracking createShipmentTracking(Shipment shipment) {
        ShipmentTracking shipmentTracking = ShipmentTracking.builder()
                .shipment(shipment)
                .status(shipment.getStatus())
                .location(shipment.getCurrentLocation())
                .description(shipment.getDescription())
                .active(true)
                .build();

        return shipmentTrackingRepository.save(shipmentTracking);
    }

    public List<ShipmentTracking> getShipmentTrackingHistory(int shipmentId) {
        return shipmentTrackingRepository.findActiveTrackingByShipmentId(shipmentId);
    }
}
