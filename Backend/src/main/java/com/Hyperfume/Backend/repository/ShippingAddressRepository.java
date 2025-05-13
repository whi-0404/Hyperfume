package com.Hyperfume.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.ShippingAddress;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {
    List<ShippingAddress> findByUserId(Integer userId);

    Optional<ShippingAddress> findByUserIdAndIsDefaultTrue(Integer userId);

    @Modifying
    @Query("UPDATE ShippingAddress s SET s.isDefault = true WHERE s.id = :addressId")
    void setDefaultAddress(@Param("addressId") Integer addressId);
}
