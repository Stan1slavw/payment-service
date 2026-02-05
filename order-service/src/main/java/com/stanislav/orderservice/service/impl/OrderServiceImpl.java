package com.stanislav.orderservice.service.impl;

import com.stanislav.orderservice.api.dto.OrderCreateEvent;
import com.stanislav.orderservice.api.dto.OrderDTO;
import com.stanislav.orderservice.api.mapper.OrderMapper;
import com.stanislav.orderservice.api.request.CreateOrderRequest;
import com.stanislav.orderservice.api.request.UpdateOrderRequest;
import com.stanislav.orderservice.api.response.OrderResponse;
import com.stanislav.orderservice.entity.Order;
import com.stanislav.orderservice.entity.enums.OrderStatus;
import com.stanislav.orderservice.repo.OrderRepository;
import com.stanislav.orderservice.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher eventPublisher;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.eventPublisher = eventPublisher;
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

        eventPublisher.publishEvent(new OrderCreateEvent(
                UUID.randomUUID(),
                saved.getId(),
                saved.getUserId(),
                saved.getAmount(),
                saved.getCurrency(),
                saved.getCreatedAt()
        ));

        return orderMapper.toOrderResponse(saved);
    }

    @Override
    @Transactional
    public OrderResponse getOrderById(UUID orderId) {
        log.info("Method name: getOrderById");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new EntityNotFoundException("Order with this id " + orderId + " not found"));
        log.info("updatedAt from entity = {}", order.getUpdatedAt());
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
    public List<OrderDTO> getAllOrders() {
        log.info("Method name: getAllOrders");
        if (orderRepository.findAll().isEmpty()) {
            log.info("Order list is empty");
        }
        return orderRepository.findAll().stream().map(orderMapper::toOrderDTO).toList();
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(UUID orderId, UpdateOrderRequest updateOrderRequest) {
        log.info("Method name: updateOrder");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new EntityNotFoundException("Order with this id " + orderId + " not found"));
        orderMapper.updateOrderFromRequest(updateOrderRequest, order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        log.info("Method name: deleteOrder");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new EntityNotFoundException("Order with this id " + orderId + " not found"));
        orderRepository.deleteById(order.getId());
    }
}
