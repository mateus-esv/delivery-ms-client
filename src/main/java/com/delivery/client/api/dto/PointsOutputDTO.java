package com.delivery.client.api.dto;

import com.delivery.client.comuns.enums.CustomerLevel;

import lombok.*;

@Builder
public record PointsOutputDTO(CustomerLevel customerLevel,Integer quantityOfOrders,Integer accumulatedPoints) {

}
