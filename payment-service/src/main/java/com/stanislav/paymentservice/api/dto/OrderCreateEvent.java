package com.stanislav.paymentservice.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
