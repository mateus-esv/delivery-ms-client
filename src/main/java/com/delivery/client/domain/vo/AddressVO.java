package com.delivery.client.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AddressVO {
    @Column(name = "state")
    private String state;
    @Column(name = "city")
    private String city;
    @Column(name = "neighborhood")
    private String neighborhood;
    @Column(name = "road")
    private String road;
    @Column(name = "number")
    private String number;
    @Column(name = "complement")
    private String complement;
}
