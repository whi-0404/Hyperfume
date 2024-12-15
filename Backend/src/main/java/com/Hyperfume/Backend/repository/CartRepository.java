package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId (Integer userId);
}
