package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.dto.response.RateResponse;
import com.Hyperfume.Backend.entity.Rate;
import com.Hyperfume.Backend.entity.serializable.RateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
    List<Rate> findByPerfumeId(Integer perfumeId);
    boolean existsById(RateKey id);
    Optional<Rate> findById(RateKey id);
}
