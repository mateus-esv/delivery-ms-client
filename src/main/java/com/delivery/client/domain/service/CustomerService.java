package com.delivery.client.domain.service;

import com.delivery.client.api.dto.CustomerIDDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private N8NRequest request;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public Customer save(Customer customer) throws CustomerException {

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
            logger.warn(
                    "Tentaiva de auto-cadastro de cliente com email, celular ou cpf já existente: email={}, celular={}, cpf={}",
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

        Customer customerSaved = repository.save(customer);

        request.sendN8N(
                MessageToN8N
                        .builder()
                        .message("Olá, " + customer.getFullName().split(" ")[0] +
                                ", seja muito bem-vindo. Sua conta foi registrada com sucesso!" +
                                "Apartir de agora te avisaremos por aqui tudo sobre seus pedidos!")
                        .phone(customer.getPhone())
                        .build());

        return customerSaved;

    }

    public Customer update(Customer customer) throws CustomerException {

        if (customer == null || customer.getId() == null) {
            logger.error("Tentativa de atualizar cliente inválido ou nulo");
            throw new CustomerException("Cliente inválido.");
        }

        // Busca cliente existente no banco
        Customer existingCustomer = repository.findById(customer.getId())
                .orElseThrow(() -> new CustomerException("Cliente não encontrado."));

        // Valida cpf, email e celular
        repository.findByCpf(customer.getCpf())
                .filter(u -> !u.getId().equals(customer.getId()))
                .ifPresent(u -> {
                    logger.warn("Cpf já cadastrado para outro cliente: {}", customer.getCpf());
                    throw new CustomerException("Cpf inválido.");
                });

        repository.findByEmail(customer.getEmail())
                .filter(u -> !u.getId().equals(customer.getId()))
                .ifPresent(u -> {
                    logger.warn("Email já cadastrado para outro cliente: {}", customer.getEmail());
                    throw new CustomerException("Email inválido.");
                });

        repository.findByPhone(customer.getPhone())
                .filter(u -> !u.getId().equals(customer.getId()))
                .ifPresent(u -> {
                    logger.warn("Celular já cadastrado para outro cliente: {}", customer.getPhone());
                    throw new CustomerException("Celular inválido.");
                });

        // Atualiza campos (mantendo id)
        existingCustomer.setFullName(customer.getFullName());
        existingCustomer.setCpf(customer.getCpf());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setAddress(customer.getAddress());

        Customer customerUpdated = repository.save(customer);

        // Não precisa atualizar os pontos, pois a pontuação é atualizado dinamicamente de acordo com as compras realizadas

        request.sendN8N(
                MessageToN8N
                        .builder()
                        .message("Olá, " + customer.getFullName().split(" ")[0]
                                + ", passando para dizer que os dados de sua conta foram atualizados com sucesso!")
                        .phone(customer.getPhone())
                        .build());

        return customerUpdated;

    }

    public Customer list(CustomerIDDTO customerIDDTO) {
        return repository.findById(customerIDDTO.id())
                .orElseThrow(() -> new CustomerException("Cliente não encontrado!"));
    }

    public List<Customer> listAll() {
        return repository.findAll();
    }

}
