package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.ShipmentTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking, Integer> {
    
    List<ShipmentTracking> findByShipmentIdOrderByTrackingTimeDesc(int shipmentId);

    @Query("SELECT s FROM ShipmentTracking s " +
            "WHERE s.shipment.id = :shipmentId AND s.active = true " +
            "ORDER BY s.trackingTime DESC")
    List<ShipmentTracking> findActiveTrackingByShipmentId(@Param("shipmentId") int shipmentId);

    @Query("SELECT s FROM ShipmentTracking s " +
            "WHERE s.shipment.id = :shipmentId " +
            "ORDER BY s.trackingTime DESC LIMIT 1")
    Optional<ShipmentTracking> findLatestTrackingByShipmentId(int shipmentId);

    @Query("SELECT s FROM ShipmentTracking s " +
            "WHERE s.shipment.order.id = :orderId AND s.active = true " +
            "ORDER BY s.trackingTime DESC")
    List<ShipmentTracking> findActiveTrackingByOrderId(@Param("orderId") int orderId);
}