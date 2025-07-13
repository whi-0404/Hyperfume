package com.Hyperfume.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hyperfume.Backend.entity.FlashSaleItem;

@Repository
public interface FlashSaleItemRepository extends JpaRepository<FlashSaleItem, Integer> {
    List<FlashSaleItem> findByFlashSaleId(int flashSaleId);

    //    @Modifying
    //    @Query("UPDATE FlashSaleItem fsi SET fsi.quantitySold = fsi.quantitySold + 1 " +
    //            "WHERE fsi.id = :flashSaleItemId " +
    //            "AND fsi.quantitySold < fsi.quantityLimit")
    //    int incrementQuantitySold(@Param("flashSaleItemId") int flashSaleItemId);
}
