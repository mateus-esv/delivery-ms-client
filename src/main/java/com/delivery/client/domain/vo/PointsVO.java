package com.delivery.client.domain.vo;

import com.delivery.client.comuns.enums.CustomerLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PointsVO {

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_level")
    private CustomerLevel customerLevel;
    @Column(name = "quantity_of_orders")
    private Integer quantityOfOrders;
    @Column(name = "accumulated_points")
    private Integer accumulatedPoints;
}
