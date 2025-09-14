package com.delivery.client.api.controller;

import com.delivery.client.api.dto.CustomerIDDTO;
import com.delivery.client.api.dto.CustomerInputDTO;
import com.delivery.client.api.dto.CustomerOutputDTO;
import com.delivery.client.api.dto.CustomerUpdateInputDTO;
import com.delivery.client.api.dto.Message;
import com.delivery.client.comuns.convert.Convert;
import com.delivery.client.domain.entity.Customer;
import com.delivery.client.domain.service.CustomerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private Convert convert;

    @Autowired
    private CustomerService service;

    @PostMapping("/save")
    public ResponseEntity<Message> save(@RequestBody CustomerInputDTO customerInputDTO) {
        Customer customer = convert.inputConvert(customerInputDTO);
        service.save(customer);
        return new ResponseEntity<Message>(
                Message.builder().message("Parabéns, você agora está registrado em nosso sistema!").build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> update(@RequestBody CustomerUpdateInputDTO customerUpdateInputDTO) {
        Customer customer = convert.inputConvert(customerUpdateInputDTO);
        service.update(customer);
            return new ResponseEntity<Message>(Message.builder().message("Suas informações foram atualizadas com sucesso em nosso sistema!").build(),
            HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<CustomerOutputDTO> list(@RequestBody CustomerIDDTO customerIDDTO) {
        return new ResponseEntity<CustomerOutputDTO>(
                convert.outputConvert(service.list(customerIDDTO)),
                HttpStatus.OK);
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<CustomerOutputDTO>> listAll() {
        return new ResponseEntity<List<CustomerOutputDTO>>(
                convert.outputConvert(service.listAll()),
                HttpStatus.OK);
    }

}
