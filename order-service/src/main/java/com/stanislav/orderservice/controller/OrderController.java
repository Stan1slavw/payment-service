package com.stanislav.orderservice.controller;

import com.stanislav.orderservice.api.dto.OrderDTO;
import com.stanislav.orderservice.api.request.CreateOrderRequest;
import com.stanislav.orderservice.api.request.UpdateOrderRequest;
import com.stanislav.orderservice.api.response.OrderResponse;
import com.stanislav.orderservice.entity.Order;
import com.stanislav.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/orders/v1")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAllOrders")
    public List<OrderDTO> getAllOrders() {
        log.info("Method name: getAllOrders");
         return orderService.getAllOrders();
    }

    @PostMapping("/createOrder")
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Controller name: /createOrder");
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable UUID id) {
        log.info("Controller name: getOrderById");
        return orderService.getOrderById(id);
    }

    @PatchMapping("/{id}")
    public OrderResponse updateOrder(@PathVariable UUID id, @RequestBody UpdateOrderRequest request) {
        log.info("Controller name: updateOrder");
        System.out.println();
        return orderService.updateOrder(id, request);
    }
}
