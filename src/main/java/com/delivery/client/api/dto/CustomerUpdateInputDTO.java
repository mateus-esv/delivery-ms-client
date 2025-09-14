package com.delivery.client.api.dto;

import java.util.UUID;

import lombok.*;

@Builder
public record CustomerUpdateInputDTO(UUID id,String fullName, String cpf, String email, String phone, AddressInputDTO addressInputDTO){
}



