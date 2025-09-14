package com.delivery.client.comuns.convert;

import com.delivery.client.api.dto.AddressOutputDTO;
import com.delivery.client.api.dto.CustomerInputDTO;
import com.delivery.client.api.dto.CustomerOutputDTO;
import com.delivery.client.api.dto.CustomerUpdateInputDTO;
import com.delivery.client.api.dto.PointsOutputDTO;
import com.delivery.client.domain.vo.AddressVO;
import com.delivery.client.domain.entity.Customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Convert {

    public Customer inputConvert(CustomerInputDTO customerInputDTO) {

        AddressVO vo = AddressVO
                .builder()
                .road(customerInputDTO.addressInputDTO().road())
                .state(customerInputDTO.addressInputDTO().state())
                .city(customerInputDTO.addressInputDTO().city())
                .neighborhood(customerInputDTO.addressInputDTO().neighborhood())
                .complement(customerInputDTO.addressInputDTO().complement())
                .number(customerInputDTO.addressInputDTO().number())
                .build();

        Customer customer = Customer
                .builder()
                .fullName(customerInputDTO.fullName())
                .cpf(customerInputDTO.cpf())
                .email(customerInputDTO.email())
                .phone(customerInputDTO.phone())
                .address(vo)
                .build();

        return customer;

    }

    public Customer inputConvert(CustomerUpdateInputDTO customerUpdateInputDTO) {

        AddressVO vo = AddressVO
                .builder()
                .road(customerUpdateInputDTO.addressInputDTO().road())
                .state(customerUpdateInputDTO.addressInputDTO().state())
                .city(customerUpdateInputDTO.addressInputDTO().city())
                .neighborhood(customerUpdateInputDTO.addressInputDTO().neighborhood())
                .complement(customerUpdateInputDTO.addressInputDTO().complement())
                .number(customerUpdateInputDTO.addressInputDTO().number())
                .build();

        Customer customer = Customer
                .builder()
                .id(customerUpdateInputDTO.id())
                .fullName(customerUpdateInputDTO.fullName())
                .cpf(customerUpdateInputDTO.cpf())
                .email(customerUpdateInputDTO.email())
                .phone(customerUpdateInputDTO.phone())
                .address(vo)
                .build();

        return customer;

    }

    public CustomerOutputDTO outputConvert(Customer customer) {

        AddressOutputDTO addressOutputDTO = AddressOutputDTO.builder()
                .city(customer.getAddress().getCity())
                .complement(customer.getAddress().getComplement())
                .neighborhood(customer.getAddress().getNeighborhood())
                .number(customer.getAddress().getNumber())
                .road(customer.getAddress().getRoad())
                .state(customer.getAddress().getState())
                .build();

        PointsOutputDTO pointsOutputDTO = PointsOutputDTO.builder()
                .accumulatedPoints(customer.getPoints().getAccumulatedPoints())
                .customerLevel(customer.getPoints().getCustomerLevel())
                .quantityOfOrders(customer.getPoints().getQuantityOfOrders())
                .build();

        CustomerOutputDTO customerOutputDTO = CustomerOutputDTO.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .cpf(customer.getCpf())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .addressOutputDTO(addressOutputDTO)
                .pointsOutputDTO(pointsOutputDTO)
                .build();

        return customerOutputDTO;
    }

    public List<CustomerOutputDTO> outputConvert(List<Customer> list) {
        List<CustomerOutputDTO> listCustomerOutputDTO = new ArrayList<CustomerOutputDTO>();
        list.forEach((customer) -> {
            listCustomerOutputDTO.add(outputConvert(customer));
        });
        return listCustomerOutputDTO;
    }

}
