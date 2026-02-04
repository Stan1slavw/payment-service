package com.stanislav.orderservice.api.mapper;

import com.stanislav.orderservice.api.dto.OrderDTO;
import com.stanislav.orderservice.api.response.OrderResponse;
import com.stanislav.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {Object.class})
public interface OrderMapper {

    @Mapping(source = "orderStatus", target = "status")
    OrderDTO toOrderDTO(Order order);

    OrderResponse toOrderResponse(Order order);
}
