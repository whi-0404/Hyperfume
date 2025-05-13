package com.Hyperfume.Backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.Hyperfume.Backend.dto.request.CartRequest;
import com.Hyperfume.Backend.dto.request.UpdateCartQuantityRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.CartResponse;
import com.Hyperfume.Backend.service.CartService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartController {
    CartService cartService;

    @PostMapping
    public ApiResponse<String> addCart(@RequestBody @Valid CartRequest request) {
        cartService.addToCart(request);
        return ApiResponse
                .<String>builder()
                .result("Successful !!")
                .build();
    }

    @GetMapping
    public ApiResponse<List<CartResponse>> getCart() {
        return ApiResponse.<List<CartResponse>>builder()
                .result(cartService.getCart())
                .build();
    }

    @PutMapping("/update_quantity")
    public ApiResponse<CartResponse> updateQuantityCart(@RequestBody UpdateCartQuantityRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.updateQuantityCart(request))
                .build();
    }

    @DeleteMapping("/{cartId}")
    public ApiResponse<String> deleteCart(@PathVariable("cartId") Integer cartId) {
        cartService.deleteCart(cartId);
        return ApiResponse.<String>builder().result("Cart has been deleted").build();
    }
}
