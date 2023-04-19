package com.ecommerce.inventoryservice;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();

			inventory.setSkuCode("iphone_12");
			inventory.setQuantity(3L);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone_11_pro");
			inventory1.setQuantity(1L);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("iphone_13");
			inventory2.setQuantity(2L);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);

		};
	}
}
