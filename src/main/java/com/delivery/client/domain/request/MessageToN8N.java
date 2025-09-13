package com.delivery.client.domain.request;

import lombok.Builder;

@Builder
public record MessageToN8N(String phone, String message) {}

