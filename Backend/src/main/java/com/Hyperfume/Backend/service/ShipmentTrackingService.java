package com.Hyperfume.Backend.service;
import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.entity.ShipmentTracking;
import com.Hyperfume.Backend.enums.ShipmentStatus;

import java.util.List;

public interface ShipmentTrackingService {


    ShipmentTracking createShipmentTracking(Shipment shipment);

    List<ShipmentTracking> getShipmentTrackingHistory(int shipmentId);
    
//    /**
//     * Create a new shipment for an order
//     * @param request Shipment details including order ID
//     * @return Created shipment details
//     */
//    ShipmentDetailResponse createShipment(CreateShipmentRequest request);
//
//    /**
//     * Get tracking information for a shipment
//     * @param shippingCode The GHN shipping tracking code
//     * @return Tracking information
//     */
//    ShipmentTrackingResponse trackShipment(String shippingCode);
//
//    /**
//     * Update status of a shipment
//     * @param request Status update information
//     * @return Updated shipment details
//     */
//    ShipmentDetailResponse updateShipmentStatus(UpdateShipmentStatusRequest request);
//
//    /**
//     * Get all shipments with specified status
//     * @param status The shipment status
//     * @return List of shipments with status
//     */
//    List<ShipmentDetailResponse> getShipmentsByStatus(ShipmentStatus status);
//
//    /**
//     * Get all shipments for a specific order
//     * @param orderId The order ID
//     * @return List of shipments for order
//     */
//    List<ShipmentDetailResponse> getShipmentsByOrder(Integer orderId);
//
//    /**
//     * Cancel a shipment
//     * @param shipmentId The shipment ID
//     * @return Cancelled shipment details
//     */
//    ShipmentDetailResponse cancelShipment(Integer shipmentId);
//
//    /**
//     * Mark a shipment as returned
//     * @param shipmentId The shipment ID
//     * @return Updated shipment details
//     */
//    ShipmentDetailResponse returnShipment(Integer shipmentId);
} 