package com.stanislav.orderservice.api.dto;

import com.stanislav.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private UUID userId;
    private BigDecimal amount;
    private String currency;
    private OrderStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
