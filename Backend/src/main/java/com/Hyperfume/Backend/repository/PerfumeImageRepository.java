package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.PerfumeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfumeImageRepository extends JpaRepository<PerfumeImage, Integer> {
    List<PerfumeImage> findByPerfumeId(Integer perfumeId);

    @Query("SELECT p FROM PerfumeImage p WHERE p.perfume.id = :perfumeId AND p.thumbnail = true")
    Optional<PerfumeImage> findByPerfumeIdAndIsThumbnailTrue(@Param("perfumeId") Integer perfumeId);

    boolean existsByPerfumeIdAndThumbnailTrue(Integer perfumeId);
}
