package com.Hyperfume.Backend.mapper.impl;

import com.Hyperfume.Backend.dto.request.FlashSaleItemRequest;
import com.Hyperfume.Backend.dto.response.FlashSaleItemResponse;
import com.Hyperfume.Backend.entity.*;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.FlashSaleItemMapper;
import com.Hyperfume.Backend.repository.PerfumeImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.util.Collections.min;

@Component
@RequiredArgsConstructor
public class FlashSaleItemMapperImpl implements FlashSaleItemMapper {
    private final PerfumeImageRepository perfumeImageRepository;

    @Override
    public FlashSaleItem toEntity(FlashSaleItemRequest request, FlashSale flashSale, Perfume perfume) {
        if (request == null || flashSale == null || perfume == null) {
            return null;
        }

        return FlashSaleItem.builder()
                .flashSale(flashSale)
                .perfume(perfume)
                .quantityLimit(request.getQuantityLimit())
                .quantitySold(0)
                .specialDiscountPercentage(request.getSpecialDiscountPercentage())
                .build();
    }

    @Override
    public FlashSaleItemResponse toResponse(FlashSaleItem flashSaleItem) {
        if(flashSaleItem == null)
            return null;

        Perfume perfume = flashSaleItem.getPerfume();

        BigDecimal originalMinPrice = getMinPrice(perfume);

        BigDecimal discountPercentage = flashSaleItem.getSpecialDiscountPercentage() != null
                ? BigDecimal.valueOf(flashSaleItem.getSpecialDiscountPercentage())
                : BigDecimal.valueOf(flashSaleItem.getFlashSale().getDiscountPercentage());

        BigDecimal hundred = new BigDecimal("100");
        BigDecimal discountFactor = BigDecimal.ONE.subtract(discountPercentage.divide(hundred, 10, RoundingMode.HALF_UP));
        BigDecimal discountedPrice = originalMinPrice.multiply(discountFactor);

        return FlashSaleItemResponse.builder()
                .id(flashSaleItem.getId())
                .perfumeId(perfume.getId())
                .perfumeName(perfume.getName())
                .perfumeImage((perfumeImageRepository.findByPerfumeIdAndIsThumbnailTrue(perfume.getId())
                        .orElseThrow(()-> new AppException(ErrorCode.THUMBNAIL_NOT_FOUND))).getImageUrl())
                .originalMinPrice(originalMinPrice)
                .discountedPrice(discountedPrice)
                .quantityLimit(flashSaleItem.getQuantityLimit())
                .quantitySold(flashSaleItem.getQuantitySold())
                .specialDiscountPercentage(flashSaleItem.getSpecialDiscountPercentage() != null
                        ? flashSaleItem.getSpecialDiscountPercentage()
                        : flashSaleItem.getFlashSale().getDiscountPercentage())
                .build();
    }

//    @Override
//    public void updateEntity(FlashSaleItem flashSaleItem, FlashSaleItemRequest request) {
//
//    }

    private BigDecimal getMinPrice(Perfume perfume) {
        return min(perfume.getVariants().stream()
                .map(PerfumeVariant::getPrice)
                .toList());
    }
}
