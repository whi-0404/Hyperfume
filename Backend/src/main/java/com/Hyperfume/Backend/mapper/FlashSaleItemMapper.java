package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.FlashSaleItemRequest;
import com.Hyperfume.Backend.dto.response.FlashSaleItemResponse;
import com.Hyperfume.Backend.entity.FlashSale;
import com.Hyperfume.Backend.entity.FlashSaleItem;
import com.Hyperfume.Backend.entity.Perfume;

public interface FlashSaleItemMapper {
    FlashSaleItem toEntity(FlashSaleItemRequest request, FlashSale flashSale, Perfume perfume);

    FlashSaleItemResponse toResponse(FlashSaleItem flashSaleItem);

//    void updateEntity(FlashSaleItem flashSaleItem, FlashSaleItemRequest request);
}
