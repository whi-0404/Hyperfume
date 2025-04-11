package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.FlashSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlashSaleRepository extends JpaRepository<FlashSale, Integer> {

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = true " +
            "AND fs.startTime <= :now " +
            "AND fs.endTime > :now " +
            "ORDER BY fs.endTime ASC")
    List<FlashSale> findActiveFlashSales(@Param("now") LocalDateTime now);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.startTime > :now ORDER BY fs.startTime ASC")
    List<FlashSale> findUpcomingFlashSales(@Param("now") LocalDateTime now);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.endTime <= :now ORDER BY fs.endTime DESC")
    List<FlashSale> findPastFlashSales(@Param("now") LocalDateTime now);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = false " +
            "AND fs.startTime <= :now " +
            "AND fs.endTime > :now " +
            "ORDER BY fs.endTime ASC")
    List<FlashSale> findInactiveCurrentFlashSales(@Param("now") LocalDateTime now);
}
