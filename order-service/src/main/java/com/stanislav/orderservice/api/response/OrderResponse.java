package com.stanislav.orderservice.api.response;

import com.stanislav.orderservice.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID userId,
        BigDecimal amount,
        String currency,
        OrderStatus orderStatus,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
