package com.Hyperfume.Backend.mapper.impl.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.Hyperfume.Backend.entity.PerfumeVariant;

@Component
public class PerfumeVariantUtil {
    public BigDecimal calculateDiscountedPrice(PerfumeVariant variant) {
        BigDecimal price = variant.getPrice();
        BigDecimal discount = BigDecimal.valueOf(variant.getPerfume().getDiscount());
        BigDecimal discountAmount = discount.divide(BigDecimal.valueOf(100)).multiply(price);
        return price.subtract(discountAmount);
    }
}
