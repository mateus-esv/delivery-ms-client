package com.delivery.client.api.dto;

import lombok.*;

@Builder
public record CustomerInputDTO(String fullName, String cpf, String email, String phone, AddressInputDTO addressInputDTO){
}



