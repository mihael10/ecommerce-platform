package com.ecommerce.inventoryservice;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.NumberFormat;


@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner displayMemoryAndCpuUsage() {
		return args -> {
			Runtime runtime = Runtime.getRuntime();
			long totalMemory = runtime.totalMemory();
			long freeMemory = runtime.freeMemory();
			long usedMemory = totalMemory - freeMemory;
			NumberFormat format = NumberFormat.getInstance();

			OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
			double cpuLoad = osBean.getSystemLoadAverage();

			System.out.println("Memory Usage: " + format.format(usedMemory / (1024 * 1024)) + " MB / " + format.format(totalMemory / (1024 * 1024)) + " MB");
			System.out.println("Memory Usage: " + format.format(usedMemory / (1024 * 1024 * 1024.0)) + " GB / " + format.format(totalMemory / (1024 * 1024 * 1024.0)) + " GB");
			System.out.println("CPU Usage: " + cpuLoad);
		};
	}
}
