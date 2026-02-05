package com.stanislav.orderservice.service.impl;

import com.stanislav.orderservice.api.mapper.OrderMapper;
import com.stanislav.orderservice.api.request.CreateOrderRequest;
import com.stanislav.orderservice.api.response.OrderResponse;
import com.stanislav.orderservice.entity.Order;
import com.stanislav.orderservice.enums.OrderStatus;
import com.stanislav.orderservice.repo.OrderRepository;
import com.stanislav.orderservice.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
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
        log.info("Method name: createOrder");
        Order order = Order.builder()
                .userId(createOrderRequest.userId())
                .amount(createOrderRequest.amount())
                .orderStatus(OrderStatus.NEW)
                .currency(createOrderRequest.currency())
                .build();

        Order saved = orderRepository.save(order);

        return orderMapper.toOrderResponse(saved);
    }

    @Override
    @Transactional
    public OrderResponse getOrderById(UUID orderId) {
        log.info("Method name: getOrderById");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new EntityNotFoundException("Order with this id " + orderId + " not found"));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public void markOrderAsPaid(UUID orderId) {
        log.info("Method name: markOrderAsPaid");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new EntityNotFoundException("Order with this id " + orderId + " not found"));
        order.markPaid();
    }

    @Override
    @Transactional
    public void markFailed(UUID orderId, String reason) {
        log.info("Method name: markFailed");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new EntityNotFoundException("Order with this id " + orderId + " not found"));
        order.markFailed();
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("Method name: getAllOrders");
        if (orderRepository.findAll().isEmpty()) {
            log.info("Order list is empty");
        }
        return orderRepository.findAll();
    }
}
