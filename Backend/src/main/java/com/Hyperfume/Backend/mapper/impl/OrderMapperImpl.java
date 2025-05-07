//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.Hyperfume.Backend.mapper.impl;

import com.Hyperfume.Backend.dto.request.order.OrderRequest;
import com.Hyperfume.Backend.dto.response.OrderResponse;
import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.Hyperfume.Backend.entity.Order;
import com.Hyperfume.Backend.entity.PaymentMethod;
import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.mapper.OrderMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements OrderMapper {
    public Order toEntity(OrderRequest request) {
        if (request == null) {
            return null;
        } else {
            Order.OrderBuilder order = Order.builder();
            order.paymentMethod(this.orderRequestToPaymentMethod(request));
            order.notes(request.getNotes());
            return order.build();
        }
    }

    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        } else {
            OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();
            orderResponse.userId(this.orderUserId(order));
            orderResponse.paymentMethodId(this.orderPaymentMethodId(order));
            orderResponse.shipmentResponse(this.shipmentToShipmentResponse(order.getShipment()));
            orderResponse.notes(order.getNotes());
            orderResponse.totalMoney(order.getTotalMoney());
            orderResponse.orderDate(order.getOrderDate());
            return orderResponse.build();
        }
    }

    protected PaymentMethod orderRequestToPaymentMethod(OrderRequest orderRequest) {
        if (orderRequest == null) {
            return null;
        } else {
            PaymentMethod.PaymentMethodBuilder paymentMethod = PaymentMethod.builder();
            if (orderRequest.getPaymentMethodId() != null) {
                paymentMethod.id(orderRequest.getPaymentMethodId());
            }

            return paymentMethod.build();
        }
    }

    private Integer orderUserId(Order order) {
        if (order == null) {
            return null;
        } else {
            User user = order.getUser();
            if (user == null) {
                return null;
            } else {
                int id = user.getId();
                return id;
            }
        }
    }

    private Integer orderPaymentMethodId(Order order) {
        if (order == null) {
            return null;
        } else {
            PaymentMethod paymentMethod = order.getPaymentMethod();
            if (paymentMethod == null) {
                return null;
            } else {
                int id = paymentMethod.getId();
                return id;
            }
        }
    }

    protected ShipmentResponse shipmentToShipmentResponse(Shipment shipment) {
        if (shipment == null) {
            return null;
        } else {
            return ShipmentResponse.builder()
                    .fee(shipment.getFee())
                    .serviceId(shipment.getServiceId())
                    .shippingAddressId(shipment.getShippingAddress().getId())
                    .shippingAddress(shipment.getShippingAddress().getShipAddress())
                    .build();
        }
    }
}
