package com.Hyperfume.Backend.mapper.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.Hyperfume.Backend.dto.request.OrderItemRequest;
import com.Hyperfume.Backend.dto.response.OrderItemResponse;
import com.Hyperfume.Backend.entity.*;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.OrderItemMapper;
import com.Hyperfume.Backend.mapper.impl.utils.PerfumeVariantUtil;
import com.Hyperfume.Backend.repository.PerfumeImageRepository;
import com.Hyperfume.Backend.repository.PerfumeVariantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderItemMapperImpl implements OrderItemMapper {
    private final PerfumeImageRepository perfumeImageRepository;
    private final PerfumeVariantUtil perfumeVariantUtil;
    private final PerfumeVariantRepository perfumeVariantRepository;

    public OrderItem toEntity(OrderItemRequest request) {
        if (request == null) {
            return null;
        } else {
            int variantId = request.getPerfumeVariantId();
            PerfumeVariant perfumeVariant = perfumeVariantRepository
                    .findById(variantId)
                    .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            Integer quantity = request.getQuantity();

            OrderItem.OrderItemBuilder orderItem = OrderItem.builder();
            orderItem.perfumeVariant(perfumeVariant);
            orderItem.totalPrice(
                    perfumeVariantUtil.calculateDiscountedPrice(perfumeVariant).multiply(BigDecimal.valueOf(quantity)));
            orderItem.quantity(quantity);
            return orderItem.build();
        }
    }

    public OrderItemResponse toResponse(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        } else {
            OrderItemResponse.OrderItemResponseBuilder orderItemResponse = OrderItemResponse.builder();
            orderItemResponse.orderId(this.orderItemOrderId(orderItem));
            orderItemResponse.perfumeName(this.orderItemPerfumeVariantPerfumeName(orderItem));
            orderItemResponse.perfumeVariantName(this.orderItemPerfumeVariantName(orderItem));
            orderItemResponse.imageData(this.orderItemImageData(orderItem));
            orderItemResponse.quantity(orderItem.getQuantity());
            orderItemResponse.variantDiscountPrice(orderItemVariantDiscountPrice(orderItem));
            orderItemResponse.totalPrice(orderItem.getTotalPrice());
            return orderItemResponse.build();
        }
    }

    protected PerfumeVariant orderItemRequestToPerfumeVariant(OrderItemRequest orderItemRequest) {
        if (orderItemRequest == null) {
            return null;
        } else {
            PerfumeVariant.PerfumeVariantBuilder perfumeVariant = PerfumeVariant.builder();
            if (orderItemRequest.getPerfumeVariantId() != null) {
                perfumeVariant.id(orderItemRequest.getPerfumeVariantId());
            }

            return perfumeVariant.build();
        }
    }

    private Integer orderItemOrderId(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        } else {
            Order order = orderItem.getOrder();
            if (order == null) {
                return null;
            } else {
                int id = order.getId();
                return id;
            }
        }
    }

    private String orderItemPerfumeVariantPerfumeName(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        } else {
            PerfumeVariant perfumeVariant = orderItem.getPerfumeVariant();
            if (perfumeVariant == null) {
                return null;
            } else {
                Perfume perfume = perfumeVariant.getPerfume();
                if (perfume == null) {
                    return null;
                } else {
                    String name = perfume.getName();
                    return name == null ? null : name;
                }
            }
        }
    }

    private String orderItemPerfumeVariantName(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        } else {
            PerfumeVariant perfumeVariant = orderItem.getPerfumeVariant();
            if (perfumeVariant == null) {
                return null;
            } else {
                String name = perfumeVariant.getName();
                return name == null ? null : name;
            }
        }
    }

    private String orderItemImageData(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        } else {
            PerfumeVariant perfumeVariant = orderItem.getPerfumeVariant();
            if (perfumeVariant == null) {
                return null;
            } else {
                Perfume perfume = perfumeVariant.getPerfume();
                if (perfume == null) {
                    return null;
                } else {
                    PerfumeImage perfumeImage = perfumeImageRepository
                            .findByPerfumeIdAndIsThumbnailTrue(perfume.getId())
                            .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND));

                    return perfumeImage.getImageUrl();
                }
            }
        }
    }

    private BigDecimal orderItemVariantDiscountPrice(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        } else {
            return perfumeVariantUtil.calculateDiscountedPrice(orderItem.getPerfumeVariant());
        }
    }
}
