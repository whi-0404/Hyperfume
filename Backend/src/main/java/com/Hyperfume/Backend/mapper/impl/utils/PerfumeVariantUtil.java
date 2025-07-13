package com.Hyperfume.Backend.mapper.impl.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.Hyperfume.Backend.entity.FlashSale;
import com.Hyperfume.Backend.entity.FlashSaleItem;
import com.Hyperfume.Backend.entity.Perfume;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import com.Hyperfume.Backend.repository.FlashSaleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PerfumeVariantUtil {
    private final FlashSaleRepository flashSaleRepository;

    //    public BigDecimal calculateDiscountedPrice(PerfumeVariant variant) {
    //        BigDecimal price = variant.getPrice();
    //        BigDecimal discount = BigDecimal.valueOf(variant.getPerfume().getDiscount());
    //        BigDecimal discountAmount = discount.divide(BigDecimal.valueOf(100)).multiply(price);
    //        return price.subtract(discountAmount);
    //    }

    public Map<String, Object> calculateFinalPrice(PerfumeVariant variant) {
        BigDecimal basePrice = variant.getPrice();
        Perfume perfume = variant.getPerfume();

        Map<String, Object> result = new HashMap<>();
        result.put("originalPrice", basePrice);
        result.put("finalPrice", basePrice);
        result.put("discountPercent", 0.0);

        if (!perfume.isSale() && !perfume.isFlash_sale()) {
            return result;
        }

        // sale
        BigDecimal discountedPrice = basePrice;
        double discountPercentage = 0.0;

        if (perfume.isSale() && perfume.getDiscount() > 0) {
            discountPercentage = perfume.getDiscount();

            BigDecimal discount = BigDecimal.valueOf(discountPercentage);
            BigDecimal discountFactor =
                    BigDecimal.ONE.subtract(discount.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP));
            discountedPrice = basePrice.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
        }

        // flash sale
        if (perfume.isFlash_sale()) {
            Optional<FlashSale> activeFlashSaleOpt = flashSaleRepository.findActiveFlashSale();

            if (activeFlashSaleOpt.isPresent()) {
                FlashSale activeFlashSale = activeFlashSaleOpt.get();
                for (FlashSaleItem item : activeFlashSale.getFlashSaleItems()) {
                    if (item.getPerfume().getId() == perfume.getId() && item.isActive()) {
                        // Lấy tỷ lệ giảm giá (ưu tiên giảm giá đặc biệt nếu có)
                            discountPercentage = item.getSpecialDiscountPercentage() != null
                                ? item.getSpecialDiscountPercentage()
                                : activeFlashSale.getDiscountPercentage();

                        BigDecimal flashSaleDiscount = BigDecimal.valueOf(discountPercentage);
                        BigDecimal flashSaleDiscountFactor = BigDecimal.ONE.subtract(
                                flashSaleDiscount.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP));

                        // Áp dụng giảm giá flash sale trên giá đã được giảm (nếu có)
                        discountedPrice = discountedPrice
                                .multiply(flashSaleDiscountFactor)
                                .setScale(2, RoundingMode.HALF_UP);
                        break;
                    }
                }
            }
        }

        result.put("finalPrice", discountedPrice);
        result.put("discountPercent", discountPercentage);

        return result;
    }
}
