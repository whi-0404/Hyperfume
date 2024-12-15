package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    boolean existsByName(String name);
}
