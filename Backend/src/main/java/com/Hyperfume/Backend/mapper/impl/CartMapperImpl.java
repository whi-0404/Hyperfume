package com.Hyperfume.Backend.mapper.impl;

import com.Hyperfume.Backend.dto.request.CartRequest;
import com.Hyperfume.Backend.dto.response.CartResponse;
import com.Hyperfume.Backend.entity.*;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.CartMapper;
import com.Hyperfume.Backend.mapper.impl.utils.CartUtil;
import com.Hyperfume.Backend.mapper.impl.utils.PerfumeImageUtil;
import com.Hyperfume.Backend.repository.PerfumeImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CartMapperImpl implements CartMapper {
    private final CartUtil cartUtil;
    private final PerfumeImageUtil perfumeImageUtil;
    private final PerfumeImageRepository perfumeImageRepository;

    @Override
    public Cart toEntity(CartRequest request) {
        if (request == null) {
            return null;
        } else {
            Cart.CartBuilder cart = Cart.builder();
            cart.perfumeVariant(this.cartRequestToPerfumeVariant(request));
            cart.user(this.cartRequestToUser(request));
            cart.quantity(request.getQuantity());
            return cart.build();
        }
    }

    @Override
    public CartResponse toResponse(Cart cart) {
        if (cart == null) {
            return null;
        } else {
            CartResponse.CartResponseBuilder cartResponse = CartResponse.builder();
            cartResponse.cartId(cart.getId());
            cartResponse.perfumeVariant(this.cartPerfumeVariantId(cart));
            cartResponse.userId(this.cartUserId(cart));
            cartResponse.variantName(this.cartPerfumeVariantName(cart));
            cartResponse.price(this.cartPerfumeVariantPrice(cart));
            cartResponse.discount(this.cartPerfumeVariantDiscount(cart));
            cartResponse.quantity(cart.getQuantity());
            cartResponse.totalPrice(cartUtil.calculateTotalPrice(cart));
            cartResponse.imageData(this.CartImageData(cart));
            return cartResponse.build();
        }
    }

    protected PerfumeVariant cartRequestToPerfumeVariant(CartRequest cartRequest) {
        if (cartRequest == null) {
            return null;
        } else {
            PerfumeVariant.PerfumeVariantBuilder perfumeVariant = PerfumeVariant.builder();
            if (cartRequest.getVariantId() != null) {
                perfumeVariant.id(cartRequest.getVariantId());
            }

            return perfumeVariant.build();
        }
    }

    protected User cartRequestToUser(CartRequest cartRequest) {
        if (cartRequest == null) {
            return null;
        } else {
            User.UserBuilder user = User.builder();
            if (cartRequest.getUserId() != null) {
                user.id(cartRequest.getUserId());
            }

            return user.build();
        }
    }

    private Integer cartPerfumeVariantId(Cart cart) {
        if (cart == null) {
            return null;
        } else {
            PerfumeVariant perfumeVariant = cart.getPerfumeVariant();
            if (perfumeVariant == null) {
                return null;
            } else {
                int id = perfumeVariant.getId();
                return id;
            }
        }
    }

    private Integer cartUserId(Cart cart) {
        if (cart == null) {
            return null;
        } else {
            User user = cart.getUser();
            if (user == null) {
                return null;
            } else {
                int id = user.getId();
                return id;
            }
        }
    }

    private String cartPerfumeVariantName(Cart cart) {
        if (cart == null) {
            return null;
        } else {
            PerfumeVariant perfumeVariant = cart.getPerfumeVariant();
            if (perfumeVariant == null) {
                return null;
            } else {
                String name = perfumeVariant.getName();
                return name == null ? null : name;
            }
        }
    }

    private BigDecimal cartPerfumeVariantPrice(Cart cart) {
        if (cart == null) {
            return null;
        } else {
            PerfumeVariant perfumeVariant = cart.getPerfumeVariant();
            if (perfumeVariant == null) {
                return null;
            } else {
                BigDecimal price = perfumeVariant.getPrice();
                return price == null ? null : price;
            }
        }
    }

    private double cartPerfumeVariantDiscount(Cart cart) {
        if (cart == null) {
            return 0.0;
        } else {
            PerfumeVariant perfumeVariant = cart.getPerfumeVariant();
            if (perfumeVariant == null) {
                return 0.0;
            } else {
                double discount = perfumeVariant.getDiscount();
                return discount;
            }
        }
    }

    private String CartImageData(Cart cart){
        if (cart == null) {
            return null;
        } else {
            PerfumeVariant perfumeVariant = cart.getPerfumeVariant();
            if (perfumeVariant == null) {
                return null;
            } else {
                Perfume perfume = perfumeVariant.getPerfume();
                if (perfume == null) {
                    return null;
                } else {
                    PerfumeImage perfumeImage = perfumeImageRepository.findByPerfumeIdAndIsThumbnailTrue(perfume.getId())
                            .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND));

                    return perfumeImageUtil.encodeImageData(perfumeImage.getImage_data());
                }
            }
        }
    }
}
