package com.Hyperfume.Backend.mapper.impl;

import com.Hyperfume.Backend.dto.request.FlashSaleRequest;
import com.Hyperfume.Backend.dto.response.FlashSaleResponse;
import com.Hyperfume.Backend.entity.FlashSale;
import com.Hyperfume.Backend.mapper.FlashSaleItemMapper;
import com.Hyperfume.Backend.mapper.FlashSaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FlashSaleMapperImpl implements FlashSaleMapper {
    private final FlashSaleItemMapper flashSaleItemMapper;

    @Override
    public FlashSaleResponse toResponse(FlashSale flashSale) {
        if (flashSale == null) {
            return null;
        }

        return FlashSaleResponse.builder()
                .id(flashSale.getId())
                .name(flashSale.getName())
                .description(flashSale.getDescription())
                .startTime(flashSale.getStartTime())
                .endTime(flashSale.getEndTime())
                .discountPercentage(flashSale.getDiscountPercentage())
                .active(flashSale.isActive())
                .createdAt(flashSale.getCreatedAt())
                .updatedAt(flashSale.getUpdatedAt())
                .flashSaleItemResponseList(flashSale.getFlashSaleItems().stream()
                        .map(flashSaleItemMapper::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public FlashSale toEntity(FlashSaleRequest request) {
        if (request == null) {
            return null;
        }

        return FlashSale.builder()
                .name(request.getName())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .discountPercentage(request.getDiscountPercentage())
//                .active(true)
                .build();
    }

    @Override
    public void updateEntity(FlashSale flashSale, FlashSaleRequest request) {
        if (request == null) {
            return;
        }

        if (request.getName() != null) {
            flashSale.setName(request.getName());
        }

        if (request.getDescription() != null) {
            flashSale.setDescription(request.getDescription());
        }

        if (request.getStartTime() != null) {
            flashSale.setStartTime(request.getStartTime());
        }

        if (request.getEndTime() != null) {
            flashSale.setEndTime(request.getEndTime());
        }

        if (request.getDiscountPercentage() > 0) {
            flashSale.setDiscountPercentage(request.getDiscountPercentage());
        }
    }
}
