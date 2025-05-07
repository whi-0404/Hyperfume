package com.Hyperfume.Backend.service;

import java.util.List;

import com.Hyperfume.Backend.dto.request.order.CreateOrderRequest;
import com.Hyperfume.Backend.dto.request.order.OrderItemRequest;
import com.Hyperfume.Backend.dto.request.order.OrderRequest;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.enums.OrderStatus;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderResponse> getAllOrders();

    void updateOrderStatus(Integer orderId, OrderStatus orderStatus);
//
//    OrderResponse getOrderById(Integer orderId);
//
//    List<OrderResponse> getOrdersByStatus(OrderStatus status);
//
//    OrderResponse updateOrderStatus(Integer orderId, OrderStatus status);
//
//    OrderResponse cancelOrder(Integer orderId);
//
//    OrderResponse processRefund(Integer orderId);
//
//    OrderResponse processReturn(Integer orderId);
//
//    OrderResponse completeOrder(Integer orderId);
}
