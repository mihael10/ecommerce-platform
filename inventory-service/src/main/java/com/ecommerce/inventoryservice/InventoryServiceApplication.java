package com.ecommerce.inventoryservice;


import com.ecommerce.inventoryservice.monitoring.SystemResourceMonitor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner displayMemoryAndCpuUsage() {
		SystemResourceMonitor monitor = new SystemResourceMonitor();
		return args -> monitor.displayMemoryAndCpuUsage();
	}

}
