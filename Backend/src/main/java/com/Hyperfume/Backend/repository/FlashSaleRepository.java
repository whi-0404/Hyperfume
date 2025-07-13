package com.Hyperfume.Backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.FlashSale;

@Repository
public interface FlashSaleRepository extends JpaRepository<FlashSale, Integer> {

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = true " + "ORDER BY fs.endTime ASC")
    Optional<FlashSale> findActiveFlashSale();

    @Query("SELECT fs FROM FlashSale fs WHERE fs.startTime > :now ORDER BY fs.startTime ASC")
    Page<FlashSale> findUpcomingFlashSales(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.endTime <= :now ORDER BY fs.endTime DESC")
    Page<FlashSale> findPastFlashSales(@Param("now") LocalDateTime now, Pageable pageable);

    //    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = false "
    //            + "AND fs.startTime <= :now "
    //            + "AND fs.endTime > :now "
    //            + "ORDER BY fs.endTime ASC")
    //    Page<FlashSale> findInactiveCurrentFlashSales(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = false "
            + "AND fs.startTime <= :now "
            + "AND fs.endTime > :now "
            + "ORDER BY fs.startTime ASC")
    List<FlashSale> findFlashSalesToActivate(@Param("now") LocalDateTime now);

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = true " + "AND fs.endTime <= :now")
    List<FlashSale> findFlashSalesToDeactivate(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(fs) > 0 FROM FlashSale fs WHERE fs.active = true")
    boolean existsActiveFlashSale();

    @Query("SELECT fs FROM FlashSale fs WHERE fs.active = true")
    boolean findByIsActiveTrue();

    @Query("SELECT COUNT(fs) > 0 FROM FlashSale fs " + "WHERE ((fs.startTime < :endTime AND fs.endTime > :startTime) "
            + "OR (fs.startTime = :startTime) OR (fs.endTime = :endTime)) "
            + "AND (fs.id != :flashSaleId OR :flashSaleId IS NULL)")
    boolean existsOverlappingFlashSale(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("flashSaleId") Integer flashSaleId);

    @Query("SELECT fs FROM FlashSale fs " + "WHERE (fs.startTime < :endTime AND fs.endTime > :startTime) "
            + "AND (fs.id != :flashSaleId OR :flashSaleId IS NULL) "
            + "ORDER BY fs.startTime ASC")
    List<FlashSale> findOverlappingFlashSales(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("flashSaleId") Integer flashSaleId);
}
