package com.Hyperfume.Backend.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.Hyperfume.Backend.ElasticSearch.ESPerfumeService;
import com.Hyperfume.Backend.entity.*;
import com.Hyperfume.Backend.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.request.OrderItemRequest;
import com.Hyperfume.Backend.dto.request.OrderRequest;
import com.Hyperfume.Backend.dto.response.OrderItemResponse;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.mapper.OrderItemMapper;
import com.Hyperfume.Backend.mapper.OrderMapper;
import com.Hyperfume.Backend.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    OrderItemRepository orderItemRepository;
    OrderItemMapper orderItemMapper;
    PerfumeVariantRepository perfumeVariantRepository;
    UserRepository userRepository;
    ShippingMethodRepository shippingMethodRepository;
    ESPerfumeService esPerfumeService;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, List<OrderItemRequest> orderItemRequests) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Integer> variantIds = orderItemRequests.stream()
                .map(OrderItemRequest :: getPerfumeVariantId)
                .toList();


        Map<Integer, PerfumeVariant> variantMaps = perfumeVariantRepository.findAllById(variantIds).stream()
                .collect(Collectors.toMap(PerfumeVariant::getId, Function.identity()));


        for(OrderItemRequest itemRequest: orderItemRequests){
            PerfumeVariant perfumeVariant = variantMaps.get(itemRequest.getPerfumeVariantId());

            if(perfumeVariant == null)
                throw new AppException(ErrorCode.VARIANT_NOT_FOUND);

            if(itemRequest.getQuantity() > perfumeVariant.getPerfume_stock_quantity()){
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

                    PerfumeVariant variant = variantMaps.get(orderItemRequest.getPerfumeVariantId());
                    variant.setPerfume_stock_quantity(variant.getPerfume_stock_quantity() - orderItemRequest.getQuantity());

                    return orderItem;
                })
                .toList();

        List<PerfumeVariant> perfumeVariants = perfumeVariantRepository.saveAll(variantMaps.values());

        orderItemRepository.saveAll(orderItems);

        List<OrderItemResponse> orderItemResponses = orderItems.stream()
                .map(orderItemMapper::toResponse)
                .toList();

        OrderResponse orderResponse = orderMapper.toResponse(orderSaved);
        orderResponse.setOrderItemResponses(orderItemResponses);

        BigDecimal itemsTotal = calculateTotalPriceItemOrder(orderResponse);

        // Calculate shipping cost and total
        ShippingMethod shippingMethod = shippingMethodRepository.findById(orderRequest.getShippingMethodId())
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_NOT_EXISTED));

        BigDecimal finalTotal = itemsTotal;

        if(shippingMethod.getShipCost() != null && shippingMethod.getShipCost().compareTo(BigDecimal.ZERO) > 0){
            finalTotal = itemsTotal.add(shippingMethod.getShipCost());
        }

        orderSaved.setTotalMoney(finalTotal);
        orderRepository.save(orderSaved);
        orderResponse.setTotalMoney(finalTotal);

        Set<Perfume> perfumeSet = perfumeVariants.stream()
                .map(PerfumeVariant::getPerfume)
                .collect(Collectors.toSet());

        esPerfumeService.indexPerfumes(perfumeSet.stream().toList());

        return orderResponse;
    }

    public List<OrderResponse> getAllOrders(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Order> orders = orderRepository.findAllByUserId(user.getId());

        return orders.stream()
                .map(order -> {
                    List<OrderItemResponse> orderItemResponse =  order.getOrderItemList().stream()
                            .map(orderItemMapper::toResponse)
                            .toList();

                    OrderResponse orderResponse = orderMapper.toResponse(order);
                    orderResponse.setOrderItemResponses(orderItemResponse);

                    return orderResponse;
                })
                .toList();
    }

    private BigDecimal calculateTotalPriceItemOrder(OrderResponse orderResponse) {
        return orderResponse.getOrderItemResponses().stream()
                .map(OrderItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
