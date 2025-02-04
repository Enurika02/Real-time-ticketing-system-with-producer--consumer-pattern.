package com.ticketing.service;

import com.ticketing.config.TicketingConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ticketing.model.SystemStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class TicketingService {
  private final TicketPool ticketPool;
  private final TicketingConfiguration config;
  private final ExecutorService executorService;
  private final Map<String, Vendor> vendors;
  private final Map<String, Customer> customers;

  public TicketingService(TicketPool ticketPool, TicketingConfiguration config) {
    this.ticketPool = ticketPool;
    this.config = config;
    this.executorService = Executors.newCachedThreadPool();
    this.vendors = new ConcurrentHashMap<>();
    this.customers = new ConcurrentHashMap<>();
  }

  public void startVendor(String vendorId) {
    if (!vendors.containsKey(vendorId)) {
      Vendor vendor = new Vendor(
          vendorId,
          ticketPool,
          config.getTicketReleaseRate(),
          1000 // 1 second interval
      );
      vendors.put(vendorId, vendor);
      executorService.submit(vendor);
      log.info("Started vendor {}", vendorId);
    }
  }

  public void stopVendor(String vendorId) {
    Vendor vendor = vendors.remove(vendorId);
    if (vendor != null) {
      vendor.stop();
      log.info("Stopped vendor {}", vendorId);
    }
  }

  public void startCustomer(String customerId) {
    if (!customers.containsKey(customerId)) {
      Customer customer = new Customer(
          customerId,
          ticketPool,
          config.getCustomerRetrievalRate());
      customers.put(customerId, customer);
      executorService.submit(customer);
      log.info("Started customer {}", customerId);
    }
  }

  public void stopCustomer(String customerId) {
    Customer customer = customers.remove(customerId);
    if (customer != null) {
      customer.stop();
      log.info("Stopped customer {}", customerId);
    }
  }

  public void shutdown() {
    vendors.forEach((id, vendor) -> vendor.stop());
    customers.forEach((id, customer) -> customer.stop());
    executorService.shutdown();
    log.info("System shutdown completed");
  }

  public SystemStatus getStatus() {
    return SystemStatus.builder()
        .availableTickets(ticketPool.getAvailableTickets())
        .soldTickets(ticketPool.getTicketsSold())
        .totalTickets(config.getTotalTickets())
        .activeVendors(vendors.keySet())
        .activeCustomers(customers.keySet())
        .build();
  }
}
