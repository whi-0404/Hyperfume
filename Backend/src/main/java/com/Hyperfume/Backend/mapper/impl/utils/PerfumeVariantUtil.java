package com.Hyperfume.Backend.mapper.impl.utils;

import com.Hyperfume.Backend.entity.PerfumeVariant;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PerfumeVariantUtil {
    public BigDecimal calculateDiscountedPrice(PerfumeVariant variant) {
        BigDecimal price = variant.getPrice();
        BigDecimal discount = BigDecimal.valueOf(variant.getDiscount());
        BigDecimal discountAmount = discount.divide(BigDecimal.valueOf(100)).multiply(price);
        return price.subtract(discountAmount);
    }
}
