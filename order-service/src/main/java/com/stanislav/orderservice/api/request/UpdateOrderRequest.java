package com.stanislav.orderservice.api.request;

import com.stanislav.orderservice.entity.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record UpdateOrderRequest(
        @NotNull
        BigDecimal amount,

        @NotNull
        String currency,

        @NotNull
        OrderStatus status
) {
}
