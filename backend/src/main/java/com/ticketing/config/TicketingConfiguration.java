package com.ticketing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ticketing")
@Data
public class TicketingConfiguration {
  protected int totalTickets;
  protected int ticketReleaseRate;
  protected int customerRetrievalRate;
  protected int maxTicketCapacity;
}
