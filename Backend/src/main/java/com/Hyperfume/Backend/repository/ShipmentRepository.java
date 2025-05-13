package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    
    List<Shipment> findByStatus(ShipmentStatus status);
    
    Optional<Shipment> findByShippingCode(String shippingCode);
    
//    @Modifying
//    @Query("UPDATE Shipment s SET s.status = :status WHERE s.id = :id")
//    void updateStatus(@Param("id") Integer id, @Param("status") ShipmentStatus status);

    @Query("SELECT s FROM Shipment s WHERE s.order.id = :orderId")
    Optional<Shipment> findByOrderId(@Param("orderId") Integer orderId);
} 