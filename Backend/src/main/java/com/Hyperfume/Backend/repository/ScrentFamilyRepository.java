package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.entity.ScrentFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrentFamilyRepository extends JpaRepository<ScrentFamily, Integer> {
    boolean existsByName(String name);
}
