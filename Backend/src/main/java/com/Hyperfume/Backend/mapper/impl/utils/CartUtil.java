package com.Hyperfume.Backend.mapper.impl.utils;

import com.Hyperfume.Backend.entity.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartUtil {
    public BigDecimal calculateTotalPrice(Cart cart) {
        BigDecimal price = cart.getPerfumeVariant().getPrice();
        BigDecimal discount = BigDecimal.valueOf(cart.getPerfumeVariant().getDiscount());
        BigDecimal discountAmount = discount.divide(BigDecimal.valueOf(100)).multiply(price);

        return price.subtract(discountAmount).multiply(BigDecimal.valueOf(cart.getQuantity()));
    }
}
