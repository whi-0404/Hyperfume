package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.FlashSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlashSaleRepository extends JpaRepository<FlashSale, Integer> {

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = true " +
            "AND fs.startTime <= :now " +
            "AND fs.endTime > :now " +
            "ORDER BY fs.endTime ASC")
    Optional<FlashSale> findActiveFlashSale(@Param("now") LocalDateTime now);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.startTime > :now ORDER BY fs.startTime ASC")
    Page<FlashSale> findUpcomingFlashSales(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.endTime <= :now ORDER BY fs.endTime DESC")
    Page<FlashSale> findPastFlashSales(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = false " +
            "AND fs.startTime <= :now " +
            "AND fs.endTime > :now " +
            "ORDER BY fs.endTime ASC")
    Page<FlashSale> findInactiveCurrentFlashSales(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = true")
    boolean findByIsActiveTrue();
}
