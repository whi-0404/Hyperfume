package com.Hyperfume.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.ShippingAddress;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {
    List<ShippingAddress> findByUserId(Integer userId);
}
