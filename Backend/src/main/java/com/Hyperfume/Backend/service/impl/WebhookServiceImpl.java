package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.dto.GHNWebhookDTO;
import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.enums.ShipmentStatus;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.repository.ShipmentRepository;
import com.Hyperfume.Backend.repository.ShipmentTrackingRepository;
import com.Hyperfume.Backend.service.ShipmentService;
import com.Hyperfume.Backend.service.ShipmentTrackingService;
import com.Hyperfume.Backend.service.WebhookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookServiceImpl implements WebhookService {
    ShipmentRepository shipmentRepository;
    ShipmentTrackingRepository shipmentTrackingRepository;
    ShipmentTrackingService shipmentTrackingService;
    ShipmentService shipmentService;

    @Override
    @Transactional
    public void processGHNWebhook(GHNWebhookDTO ghnWebhookDTO) {
        String orderCode = ghnWebhookDTO.getOrderCode();
        Shipment shipment = shipmentRepository.findByShippingCode(orderCode)
                .orElseThrow(()-> new AppException(ErrorCode.SHIPMENT_NOT_EXISTED));

        ShipmentStatus newStatus = mapGHNStatusToShipmentStatus(ghnWebhookDTO.getStatus());

        shipmentService.updateShipmentStatus(shipment, newStatus, ghnWebhookDTO.getWareHouse(), ghnWebhookDTO.getDescription());

        //Send notification
    }

    private ShipmentStatus mapGHNStatusToShipmentStatus(String ghnStatus){
        return ShipmentStatus.valueOf(ghnStatus.toUpperCase());
    }
}
