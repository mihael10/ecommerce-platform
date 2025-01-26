package com.ecommerce.inventoryservice.monitoring;



import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.NumberFormat;

public class SystemResourceMonitor {

    private static final int BYTES_TO_MB = 1024 * 1024;
    private static final double BYTES_TO_GB = 1024 * 1024 * 1024.0;

    public void displayMemoryAndCpuUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        NumberFormat format = NumberFormat.getInstance();

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getSystemLoadAverage();

        // Use formatted output for readability
        System.out.printf("Memory Usage: %.2f GB used out of %.2f GB total%n",
                usedMemory / BYTES_TO_GB, totalMemory / BYTES_TO_GB);

        if (cpuLoad < 0) {
            System.out.println("CPU Usage information not available");
        } else {
            System.out.printf("CPU Usage: %.2f%n", cpuLoad);
        }
    }



}
