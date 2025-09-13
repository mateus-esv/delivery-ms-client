package com.delivery.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class DeliveryMsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryMsClientApplication.class, args);
	}

}
