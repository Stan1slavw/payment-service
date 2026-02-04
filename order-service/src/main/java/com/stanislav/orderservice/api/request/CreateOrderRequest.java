package com.stanislav.orderservice.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull
        UUID userId,

        @NotNull
        @Positive
        BigDecimal amount,

        @NotNull
        String currency
) {}