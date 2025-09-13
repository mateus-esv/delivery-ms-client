package com.delivery.client.domain.service;

import com.delivery.client.comuns.enums.CustomerLevel;
import com.delivery.client.comuns.exception.CustomerException;
import com.delivery.client.domain.entity.Customer;
import com.delivery.client.domain.repository.CustomerRepository;
import com.delivery.client.domain.request.MessageToN8N;
import com.delivery.client.domain.request.N8NRequest;
import com.delivery.client.domain.vo.PointsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private N8NRequest request;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public Customer save(Customer customer) throws CustomerException{

        if (customer == null) {
            logger.error("Tentativa de salvar usuário nulo");
            throw new CustomerException("Dados inválido.");
        }

        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID());
        }

        Optional<Customer> existingEmail = repository.findByEmail(customer.getEmail());
        Optional<Customer> existingPhone = repository.findByPhone(customer.getPhone());
        Optional<Customer> existingCpf = repository.findByCpf(customer.getCpf());

        if (existingEmail.isPresent() || existingPhone.isPresent() || existingCpf.isPresent()) {
            logger.warn("Tentaiva de auto-cadastro de cliente com email, celular ou cpf já existente: email={}, celular={}, cpf={}",
                    customer.getEmail(), customer.getPhone(), customer.getCpf());
            throw new CustomerException("Cadastro inválido: email, celular ou cpf já cadastrado.");
        }

        PointsVO pointsVO = PointsVO
                .builder()
                .quantityOfOrders(0)
                .accumulatedPoints(0)
                .customerLevel(CustomerLevel.BRONZE)
                .build();

        customer.setPoints(pointsVO);

        request.sendN8N(
            MessageToN8N
                .builder()
                .message("Olá, "+customer.getFullName().split(" ")[0] +
                        ", seja muito bem-vindo. Sua conta foi registrada com sucesso!" +
                        "Apartir de agora te avisaremos por aqui tudo sobre seus pedidos!")
                .phone(customer.getPhone())
                .build());

        return repository.save(customer);
    }

}
