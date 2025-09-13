package com.delivery.client.api.dto;

import lombok.Builder;
import java.util.UUID;

@Builder
public record CustomerIDDTO(UUID id){
}