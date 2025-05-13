package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.FlashSaleRequest;
import com.Hyperfume.Backend.dto.response.FlashSaleResponse;
import com.Hyperfume.Backend.entity.FlashSale;

public interface FlashSaleMapper {
    FlashSaleResponse toResponse(FlashSale flashSale);

    FlashSale toEntity(FlashSaleRequest request);

    void updateEntity(FlashSale flashSale, FlashSaleRequest request);
}
