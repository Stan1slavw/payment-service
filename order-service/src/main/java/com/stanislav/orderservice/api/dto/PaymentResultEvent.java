package com.stanislav.orderservice.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PaymentResultEvent(
        UUID eventId,
        UUID orderId,
        UUID paymentId,
        PaymentStatus status,
        String reason,
        OffsetDateTime occurredAt
) {
    public enum PaymentStatus { SUCCEEDED, FAILED }
}
