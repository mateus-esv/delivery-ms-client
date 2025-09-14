package com.delivery.client.api.dto;

import lombok.Builder;

@Builder
public record AddressOutputDTO(String state, String city, String neighborhood, String road, String number, String complement) { }
