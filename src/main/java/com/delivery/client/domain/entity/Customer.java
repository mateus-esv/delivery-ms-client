package com.delivery.client.domain.entity;

import com.delivery.client.domain.vo.AddressVO;
import com.delivery.client.domain.vo.PointsVO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_client")
public class Customer {

    @Id
    private UUID id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "cpf", unique = true)
    private String cpf;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone", unique = true)
    private String phone;

    @Embedded
    private PointsVO points;

    @Embedded
    private AddressVO address;

}
