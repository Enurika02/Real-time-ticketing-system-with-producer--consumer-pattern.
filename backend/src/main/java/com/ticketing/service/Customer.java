package com.ticketing.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Customer implements Runnable {
    private final String customerId;
    private final TicketPool ticketPool;
    private final long retrievalInterval;
    private volatile boolean running;

    public Customer(String customerId, TicketPool ticketPool, long retrievalInterval) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
        this.running = true;
    }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                var ticket = ticketPool.removeTicket(customerId);
                if (ticket != null) {
                    log.info("Customer {} purchased ticket {}", 
                        customerId, ticket.getId());
                }
                Thread.sleep(retrievalInterval);
            } catch (InterruptedException e) {
                log.error("Customer {} interrupted", customerId);
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }
}