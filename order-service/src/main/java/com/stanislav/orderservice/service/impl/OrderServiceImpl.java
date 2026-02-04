package com.stanislav.orderservice.service.impl;

import com.stanislav.orderservice.api.mapper.OrderMapper;
import com.stanislav.orderservice.api.request.CreateOrderRequest;
import com.stanislav.orderservice.api.response.OrderResponse;
import com.stanislav.orderservice.entity.Order;
import com.stanislav.orderservice.repo.OrderRepository;
import com.stanislav.orderservice.service.OrderService;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(@NonNull CreateOrderRequest createOrderRequest) {
        Order order = Order.builder()
                .userId(createOrderRequest.userId())
                .amount(createOrderRequest.amount())
                .currency(createOrderRequest.currency())
                .build();

        Order saved = orderRepository.save(order);

        return orderMapper.toOrderResponse(saved);
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {
        return null;
    }

    @Override
    public void markOrderAsPaid(UUID orderId) {

    }

    @Override
    public void markFailed(UUID orderId, String reason) {

    }
}
