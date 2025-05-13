package com.Hyperfume.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByName(String name);
}
