package com.ticketing.service;

import com.ticketing.config.TicketingConfiguration;
import com.ticketing.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class TicketPool {
  private final BlockingQueue<Ticket> tickets;
  private final TicketingConfiguration config;
  private int ticketsSold;

  public TicketPool(TicketingConfiguration config) {
    this.config = config;
    this.tickets = new LinkedBlockingQueue<>(config.getMaxTicketCapacity());
    this.ticketsSold = 0;
  }

  public synchronized boolean addTickets(String vendorId, int count) {
    if (tickets.size() + count > config.getMaxTicketCapacity()) {
      log.warn("Cannot add tickets: pool at capacity");
      return false;
    }

    // Not issuing after totalTickets have been reached
    if (ticketsSold > config.getTotalTickets()) {
      log.warn("Cannot add tickets: all tickets have been sold");
      return false;
    }

    for (int i = 0; i < count; i++) {
      Ticket ticket = new Ticket();
      ticket.setVendorId(vendorId);
      ticket.setStatus("AVAILABLE");
      ticket.setTimestamp(System.currentTimeMillis());

      try {
        tickets.put(ticket);
        log.info("Vendor {} added ticket.", vendorId);
      } catch (InterruptedException e) {
        log.error("Error adding ticket", e);
        Thread.currentThread().interrupt();
        return false;
      }
    }
    return true;
  }

  public synchronized Ticket removeTicket(String customerId) {
    try {
      Ticket ticket = tickets.poll();
      if (ticket != null) {
        log.info("Customer {} purchased ticket from vendor {}",
            customerId, ticket.getVendorId());
        ticketsSold++;
        return ticket;
      }
    } catch (Exception e) {
      log.error("Error removing ticket", e);
    }
    return null;
  }

  public int getAvailableTickets() {
    return tickets.size();
  }

  public int getTicketsSold() {
    return ticketsSold;
  }
}
