package com.Hyperfume.Backend.mapper;

import com.Hyperfume.Backend.dto.request.CartRequest;
import com.Hyperfume.Backend.dto.response.CartResponse;
import com.Hyperfume.Backend.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface CartMapper {
    Cart toEntity(CartRequest request);

    CartResponse toResponse(Cart cart);

}
