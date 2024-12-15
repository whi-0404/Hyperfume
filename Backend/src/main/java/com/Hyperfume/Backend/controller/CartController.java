package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.request.CartRequest;
import com.Hyperfume.Backend.dto.request.UpdateCartQuantityRequest;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.dto.response.CartResponse;
import com.Hyperfume.Backend.service.CartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartController {
    CartService cartService;

    @PostMapping
    public ApiResponse<Void> addCart(@RequestBody @Valid CartRequest request){
        cartService.addToCart(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<CartResponse>> getCart(@PathVariable("userId") Integer userId){
        return ApiResponse.<List<CartResponse>>builder()
                .result(cartService.getCart(userId))
                .build();
    }

    @PutMapping("/update_quantity")
    public ApiResponse<CartResponse> updateQuantityCart(@RequestBody UpdateCartQuantityRequest request){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.updateQuantityCart(request))
                .build();
    }

    @DeleteMapping("/{cartId}")
    public ApiResponse<String> deleteCart(@PathVariable("cartId") Integer cartId){
        return ApiResponse.<String>builder()
                .result("Cart has been deleted")
                .build();
    }
}
