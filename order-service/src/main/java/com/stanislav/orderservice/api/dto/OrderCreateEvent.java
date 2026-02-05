package com.stanislav.orderservice.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.UUID;

public record OrderCreateEvent(
        UUID eventId,
        UUID orderId,
        UUID userId,
        BigDecimal amount,
        String currency,
        OffsetDateTime createdAt
) {
}
