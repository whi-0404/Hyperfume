package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.PerfumeVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumeVariantRepository extends JpaRepository<PerfumeVariant, Integer> {
    List<PerfumeVariant> findByPerfumeId(Integer perfumeId);
}
