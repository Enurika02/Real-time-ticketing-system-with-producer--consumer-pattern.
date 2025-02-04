package com.ticketing.controller;

import com.ticketing.model.SystemStatus;
import com.ticketing.service.TicketingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticketing")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TicketingController {
  private final TicketingService ticketingService;

  @PostMapping("/vendors/{vendorId}/start")
  public void startVendor(@PathVariable String vendorId) {
    ticketingService.startVendor(vendorId);
  }

  @PostMapping("/vendors/{vendorId}/stop")
  public void stopVendor(@PathVariable String vendorId) {
    ticketingService.stopVendor(vendorId);
  }

  @PostMapping("/customers/{customerId}/start")
  public void startCustomer(@PathVariable String customerId) {
    ticketingService.startCustomer(customerId);
  }

  @PostMapping("/customers/{customerId}/stop")
  public void stopCustomer(@PathVariable String customerId) {
    ticketingService.stopCustomer(customerId);
  }

  @GetMapping("/status")
  public SystemStatus getStatus() {
    return ticketingService.getStatus();
  }
}
