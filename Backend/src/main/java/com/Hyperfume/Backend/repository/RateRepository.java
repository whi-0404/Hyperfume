package com.Hyperfume.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.Rate;
import com.Hyperfume.Backend.entity.serializable.RateKey;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
    List<Rate> findByPerfumeId(Integer perfumeId);

    boolean existsById(RateKey id);

    Optional<Rate> findById(RateKey id);
}
