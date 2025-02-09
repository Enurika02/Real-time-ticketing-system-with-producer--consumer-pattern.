package com.ticketing.model;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemStatus {
  private int availableTickets;
  private int soldTickets;
  private int totalTickets;
  private Set<String> activeVendors;
  private Set<String> activeCustomers;
}
