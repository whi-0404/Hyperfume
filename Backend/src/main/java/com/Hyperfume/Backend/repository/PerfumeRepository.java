package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Integer> {
    @Modifying
    @Query("UPDATE Perfume p SET p.sale = :sale WHERE p.id = :perfumeId")
    void updateSaleStatus(@Param("perfumeId") Integer perfumeId, @Param("sale") Boolean sale);

    @Modifying
    @Query("UPDATE Perfume p SET p.flash_sale = :flashSale WHERE p.id = :perfumeId")
    void updateFlashSaleStatus(@Param("perfumeId") Integer perfumeId, @Param("flashSale") boolean flashSale);

    @Query("SELECT p FROM Perfume p WHERE p.flash_sale = true")
    List<Perfume> findAllFlashSaleProducts();
}
