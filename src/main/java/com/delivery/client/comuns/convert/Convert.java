package com.delivery.client.comuns.convert;

import com.delivery.client.api.dto.CustomerInputDTO;
import com.delivery.client.domain.vo.AddressVO;
import com.delivery.client.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class Convert {

    public Customer inputConvert(CustomerInputDTO customerInputDTO){

        Customer customer = Customer
                .builder()
                .fullName(customerInputDTO.fullName())
                .cpf(customerInputDTO.cpf())
                .email(customerInputDTO.email())
                .phone(customerInputDTO.phone())
                .build();

        AddressVO vo = AddressVO
                .builder()
                .road(customerInputDTO.addressInputDTO().road())
                .state(customerInputDTO.addressInputDTO().state())
                .city(customerInputDTO.addressInputDTO().city())
                .neighborhood(customerInputDTO.addressInputDTO().neighborhood())
                .complement(customerInputDTO.addressInputDTO().complement())
                .number(customerInputDTO.addressInputDTO().number())
                .build();

        customer.setAddress(vo);

        return customer;

    }


}
