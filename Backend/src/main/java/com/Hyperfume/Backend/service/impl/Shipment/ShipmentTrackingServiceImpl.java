package com.Hyperfume.Backend.service.impl.Shipment;

import com.Hyperfume.Backend.entity.Order;
import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.entity.ShipmentTracking;
import com.Hyperfume.Backend.enums.OrderStatus;
import com.Hyperfume.Backend.enums.ShipmentStatus;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.repository.OrderRepository;
import com.Hyperfume.Backend.repository.ShipmentRepository;
import com.Hyperfume.Backend.repository.ShipmentTrackingRepository;
import com.Hyperfume.Backend.service.ShipmentService;
import com.Hyperfume.Backend.service.ShipmentTrackingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
