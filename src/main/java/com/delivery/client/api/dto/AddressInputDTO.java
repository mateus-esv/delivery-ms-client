package com.delivery.client.api.dto;

import lombok.Builder;

@Builder
public record AddressInputDTO(String state, String city, String neighborhood, String road, String number, String complement) { }
