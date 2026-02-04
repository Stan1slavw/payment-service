package com.stanislav.orderservice.service;

import com.stanislav.orderservice.api.request.CreateOrderRequest;
import com.stanislav.orderservice.api.response.OrderResponse;

import java.util.UUID;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest createOrderRequest);

    OrderResponse getOrderById(UUID orderId);

    void markOrderAsPaid(UUID orderId);

    void markFailed(UUID orderId, String reason);
}
