package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.CartRequest;
import com.Hyperfume.Backend.dto.request.UpdateCartQuantityRequest;
import com.Hyperfume.Backend.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    void addToCart(CartRequest request);
    List<CartResponse> getCart();
    CartResponse updateQuantityCart(UpdateCartQuantityRequest request);
    void deleteCart(Integer cartId);
}
