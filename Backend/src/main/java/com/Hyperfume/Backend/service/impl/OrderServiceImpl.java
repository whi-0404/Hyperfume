package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.dto.request.OrderItemRequest;
import com.Hyperfume.Backend.dto.request.OrderRequest;
import com.Hyperfume.Backend.dto.response.OrderItemResponse;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.entity.Order;
import com.Hyperfume.Backend.entity.OrderItem;
import com.Hyperfume.Backend.entity.PerfumeVariant;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.OrderItemMapper;
import com.Hyperfume.Backend.mapper.OrderMapper;
import com.Hyperfume.Backend.repository.OrderItemRepository;
import com.Hyperfume.Backend.repository.OrderRepository;
import com.Hyperfume.Backend.repository.PerfumeVariantRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import com.Hyperfume.Backend.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    OrderItemRepository orderItemRepository;
    OrderItemMapper orderItemMapper;
    PerfumeVariantRepository perfumeVariantRepository;
    UserRepository userRepository;

    public OrderResponse createOrder(OrderRequest orderRequest, List<OrderItemRequest> orderItemRequests) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Kiểm tra tồn kho
        for (OrderItemRequest itemRequest : orderItemRequests) {
            int stockQuantity = perfumeVariantRepository.findStockByPerfumeVariantId(itemRequest.getPerfumeVariantId());
            if (itemRequest.getQuantity() > stockQuantity) {
                throw new AppException(ErrorCode.OUT_OF_STOCK);
            }
        }

        Order order = orderMapper.toEntity(orderRequest);
        order.setUser(user);
        Order orderSaved = orderRepository.save(order);

        List<OrderItem> orderItems = orderItemRequests.stream()
                .map(orderItemRequest -> {
                    OrderItem orderItem = orderItemMapper.toEntity(orderItemRequest);
                    orderItem.setOrder(orderSaved);


                    PerfumeVariant perfumeVariant = perfumeVariantRepository.findById(orderItemRequest.getPerfumeVariantId())
                            .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
                    int updatedStock = perfumeVariant.getPerfume_stock_quantity() - orderItemRequest.getQuantity();
                    if (updatedStock < 0) {
                        throw new IllegalArgumentException("Số lượng tồn kho không đủ cho sản phẩm ID: " + orderItemRequest.getPerfumeVariantId());
                    }
                    perfumeVariant.setPerfume_stock_quantity(updatedStock);
                    perfumeVariantRepository.save(perfumeVariant);

                    return orderItem;
                })
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        List<OrderItemResponse> orderItemResponses = orderItems.stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());

        OrderResponse orderResponse = orderMapper.toResponse(orderSaved);
        orderResponse.setOrderItemResponses(orderItemResponses);

        orderSaved.setTotalMoney(calculateTotalPriceOrder(orderResponse));
        orderRepository.save(orderSaved);

        return orderResponse;
    }
    private BigDecimal calculateTotalPriceOrder(OrderResponse orderResponse) {
        return orderResponse.getOrderItemResponses()
                .stream()
                .map(OrderItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
