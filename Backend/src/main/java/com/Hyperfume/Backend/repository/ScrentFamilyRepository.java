package com.Hyperfume.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.ScrentFamily;

@Repository
public interface ScrentFamilyRepository extends JpaRepository<ScrentFamily, Integer> {
    boolean existsByName(String name);
}
