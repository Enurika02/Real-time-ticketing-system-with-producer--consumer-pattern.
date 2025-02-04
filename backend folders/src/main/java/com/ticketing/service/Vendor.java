package com.ticketing.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Vendor implements Runnable {
    private final String vendorId;
    private final TicketPool ticketPool;
    private final int ticketsPerRelease;
    private final long releaseInterval;
    private volatile boolean running;

    public Vendor(String vendorId, TicketPool ticketPool, 
                 int ticketsPerRelease, long releaseInterval) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.running = true;
    }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                boolean added = ticketPool.addTickets(vendorId, ticketsPerRelease);
                if (added) {
                    log.info("Vendor {} released {} tickets", 
                        vendorId, ticketsPerRelease);
                }
                Thread.sleep(releaseInterval);
            } catch (InterruptedException e) {
                log.error("Vendor {} interrupted", vendorId);
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }
}