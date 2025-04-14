package com.Hyperfume.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(Integer userId);

    @Query("SELECT COUNT(*) > 0 FROM Cart WHERE user.id = :userId AND perfumeVariant.id = :variantId")
    boolean existedByUserIdAndVariantId(Integer userId, Integer variantId);
}
