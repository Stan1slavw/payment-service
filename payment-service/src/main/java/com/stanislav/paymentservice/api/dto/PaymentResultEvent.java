package com.stanislav.paymentservice.api.dto;


import com.stanislav.paymentservice.entity.enums.PaymentStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PaymentResultEvent(
        UUID eventId,
        UUID orderId,
        UUID paymentId,
        PaymentStatus status,
        String reason,
        OffsetDateTime occurredAt
) {}