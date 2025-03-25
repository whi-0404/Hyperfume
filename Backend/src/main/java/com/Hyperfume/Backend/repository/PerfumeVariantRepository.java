package com.Hyperfume.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.PerfumeVariant;

@Repository
public interface PerfumeVariantRepository extends JpaRepository<PerfumeVariant, Integer> {
    List<PerfumeVariant> findByPerfumeId(Integer perfumeId);

    @Query("SELECT p.perfume_stock_quantity FROM PerfumeVariant p WHERE p.id = :perfumeVariantId")
    int findStockByPerfumeVariantId(@Param("perfumeVariantId") Integer perfumeVariantId);
}
