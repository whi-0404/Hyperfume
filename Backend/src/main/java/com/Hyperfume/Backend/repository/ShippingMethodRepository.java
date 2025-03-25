package com.Hyperfume.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.ShippingMethod;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
    boolean existsByName(String name);
}
