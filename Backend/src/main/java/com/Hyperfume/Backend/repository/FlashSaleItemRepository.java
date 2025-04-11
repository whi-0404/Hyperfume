package com.Hyperfume.Backend.repository;

import com.Hyperfume.Backend.dto.response.FlashSaleItemResponse;
import com.Hyperfume.Backend.entity.FlashSaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashSaleItemRepository extends JpaRepository<FlashSaleItem, Integer> {
    List<FlashSaleItem> findByFlashSaleId(int flashSaleId);

//    @Modifying
//    @Query("UPDATE FlashSaleItem fsi SET fsi.quantitySold = fsi.quantitySold + 1 " +
//            "WHERE fsi.id = :flashSaleItemId " +
//            "AND fsi.quantitySold < fsi.quantityLimit")
//    int incrementQuantitySold(@Param("flashSaleItemId") int flashSaleItemId);
}
