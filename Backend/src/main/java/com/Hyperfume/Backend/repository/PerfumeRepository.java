package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.dto.response.PerfumeGetAllResponse;
import com.Hyperfume.Backend.entity.Perfume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Integer>, JpaSpecificationExecutor<Perfume> {
    @Modifying
    @Query("UPDATE Perfume p SET p.sale = :sale WHERE p.id = :perfumeId")
    void updateSaleStatus(@Param("perfumeId") Integer perfumeId, @Param("sale") Boolean sale);

    @Modifying
    @Query("UPDATE Perfume p SET p.flash_sale = :flashSale WHERE p.id = :perfumeId")
    void updateFlashSaleStatus(@Param("perfumeId") Integer perfumeId, @Param("flashSale") boolean flashSale);

    @Query("SELECT p FROM Perfume p WHERE p.flash_sale = true")
    Page<Perfume> findAllByFlashSale(Pageable pageable);

    @Query("SELECT p FROM Perfume p WHERE p.type = :typeName")
    Page<Perfume> findAllByTypeName(@Param("typeName") String typeName, Pageable pageable);

    @Query("SELECT p FROM Perfume p WHERE p.perfume_gender = :perfume_gender")
    Page<Perfume> findAllByGender(@Param("perfume_gender") String perfume_gender, Pageable pageable);

    @Query("SELECT p FROM Perfume p WHERE p.brand.id = :brandId")
    Page<Perfume> findAllByBrandId(@Param("brandId") Integer brandId, Pageable pageable);

    @Query("SELECT p FROM Perfume p WHERE p.country.id = :countryId")
    Page<Perfume> findAllByCountryId(@Param("countryId") Integer countryId, Pageable pageable);

    @Query("SELECT p FROM Perfume p WHERE p.screntFamily.id = :screntFamilyId")
    Page<Perfume> findAllByScrentFamilyId(@Param("screntFamilyId") Integer screntFamilyId, Pageable pageable);

    @Query("SELECT p FROM Perfume p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Perfume> searchByName(@Param("name") String name, Pageable pageable);

}
