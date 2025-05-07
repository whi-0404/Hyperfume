package com.Hyperfume.Backend.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.Hyperfume.Backend.ElasticSearch.ESPerfumeService;
import com.Hyperfume.Backend.dto.request.order.CreateOrderRequest;
import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.Hyperfume.Backend.entity.*;
import com.Hyperfume.Backend.enums.OrderStatus;
import com.Hyperfume.Backend.mapper.ShipmentMapper;
import com.Hyperfume.Backend.repository.*;
import com.Hyperfume.Backend.service.NotificationService;
import com.Hyperfume.Backend.service.ShipmentService;
import com.Hyperfume.Backend.service.redis.ShipmentRedisService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.request.order.OrderItemRequest;
import com.Hyperfume.Backend.dto.request.order.OrderRequest;
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
    ESPerfumeService esPerfumeService;
    ShipmentRedisService shipmentRedisService;
    ShipmentService shipmentService;
    ShipmentMapper shipmentMapper;
    NotificationService notificationService;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        OrderRequest orderRequest = request.getOrderRequest();
        List<OrderItemRequest> orderItemRequests = request.getOrderItemRequests();
        String shipmentToken = request.getShipmentToken();

        if(shipmentToken == null || shipmentToken.isEmpty()){
            throw new AppException(ErrorCode.SHIPMENT_TOKEN_INVALID);
        }

        //Check stock of perfume
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
        //

        //create and save order
        Order order = orderMapper.toEntity(orderRequest);
        order.setUser(user);
        order.setStatus(OrderStatus.ORDER_PENDING);
        Order orderSaved = orderRepository.save(order);

        //Create and save orderItems
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


        //Calculate the total price of items
        List<OrderItemResponse> orderItemResponses = orderItems.stream()
                .map(orderItemMapper::toResponse)
                .toList();

        OrderResponse orderResponse = orderMapper.toResponse(orderSaved);
        orderResponse.setOrderItemResponses(orderItemResponses);

        BigDecimal itemsTotal = calculateTotalPriceItemOrder(orderResponse);

        // Calculate shipping cost and total
        ShipmentResponse response = shipmentRedisService.getShipmentInfo(shipmentToken);

        log.info(String.valueOf(response));

        BigDecimal finalTotal = itemsTotal.add(BigDecimal.valueOf(response.getFee()));

        orderSaved.setTotalMoney(finalTotal);
        orderResponse.setTotalMoney(finalTotal);

        Shipment shipment;
        try{
             shipment = shipmentService.createShipment(orderSaved, shipmentToken);
        } catch (Exception e){
            throw new AppException(ErrorCode.SHIPMENT_CREATION_FAILED);
        }

        orderResponse.setShipmentResponse(shipmentMapper.toResponse(shipment));

        //update ElasticSearch
        Set<Perfume> perfumeSet = perfumeVariants.stream()
                .map(PerfumeVariant::getPerfume)
                .collect(Collectors.toSet());

        esPerfumeService.indexPerfumes(perfumeSet.stream().toList());


        //send notification to customer
        notificationService.sendOrderStatusNotification(
                user.getId(),
                "Đặt hàng thành công",
                "Đơn hàng của bạn đã được tạo với sản phẩm: " +
                        orderItemResponses.stream()
                                .map(item -> item.getPerfumeName() + " "
                                        + item.getPerfumeVariantName())
                                .collect(Collectors.joining(", "))
                + ". Tổng tiền: " + finalTotal + " VND"
        );

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

    public void updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        order.setStatus(orderStatus);

        orderRepository.save(order);
    }

    private BigDecimal calculateTotalPriceItemOrder(OrderResponse orderResponse) {
        return orderResponse.getOrderItemResponses().stream()
                .map(OrderItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
