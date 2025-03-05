package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.dto.request.CartRequest;
import com.Hyperfume.Backend.dto.request.UpdateCartQuantityRequest;
import com.Hyperfume.Backend.dto.response.CartResponse;
import com.Hyperfume.Backend.entity.Cart;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.CartMapper;
import com.Hyperfume.Backend.repository.CartRepository;
import com.Hyperfume.Backend.repository.PerfumeVariantRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import com.Hyperfume.Backend.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    CartMapper cartMapper;
    PerfumeVariantRepository variantRepository;
    UserRepository userRepository;

    public void addToCart(CartRequest request){
        PerfumeVariant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        if(variant.getPerfume_stock_quantity() < request.getQuantity()){
            throw new AppException(ErrorCode.NOT_ENOUGH_STOCK_AVAILABLE);
        }

        Cart cart = cartMapper.toEntity(request);

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        cart.setUser(user);

        cartRepository.save(cart);
    }

    public List<CartResponse> getCart(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Cart> cart = cartRepository.findByUserId(user.getId());

        return cart.stream()
                .map(cartMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CartResponse updateQuantityCart(UpdateCartQuantityRequest request){
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() ->  new AppException(ErrorCode.CART_NOT_FOUND));


        if(cart.getPerfumeVariant().getPerfume_stock_quantity() < request.getQuantity()){
            throw new AppException(ErrorCode.NOT_ENOUGH_STOCK_AVAILABLE);
        }

        cart.setQuantity(request.getQuantity());

        return cartMapper.toResponse(cartRepository.save(cart));
    }

    public void deleteCart(Integer cartId){
        cartRepository.deleteById(cartId);
    }



}
