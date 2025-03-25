package com.Hyperfume.Backend.mapper.impl.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.Hyperfume.Backend.entity.Cart;

@Component
public class CartUtil {
    public BigDecimal calculateTotalPrice(Cart cart) {
        BigDecimal price = cart.getPerfumeVariant().getPrice();
        BigDecimal discount =
                BigDecimal.valueOf(cart.getPerfumeVariant().getPerfume().getDiscount());
        BigDecimal discountAmount = discount.divide(BigDecimal.valueOf(100)).multiply(price);

        return price.subtract(discountAmount).multiply(BigDecimal.valueOf(cart.getQuantity()));
    }
}
