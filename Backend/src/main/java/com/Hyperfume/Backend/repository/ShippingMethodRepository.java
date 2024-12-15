package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
    boolean existsByName(String name);
}
