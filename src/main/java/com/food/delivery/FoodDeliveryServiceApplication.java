package com.food.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodDeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryServiceApplication.class, args);
	}

}
