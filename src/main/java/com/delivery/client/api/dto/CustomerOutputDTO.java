package com.delivery.client.api.dto;

import com.delivery.client.domain.vo.AddressVO;
import com.delivery.client.domain.vo.PointsVO;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerOutputDTO(UUID id, String fullName, String cpf, String email, String phone, AddressVO address, PointsVO points){
}
