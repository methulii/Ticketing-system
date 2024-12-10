package com.example.ticketing.threads;

import com.example.ticketing.service.TicketPool;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;  // Shared TicketPool instance
    private final int releaseRate;        // Number of tickets the vendor will add each time

    // Constructor to initialize Vendor with a TicketPool and ticket release rate
    public Vendor(TicketPool ticketPool, int releaseRate) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Add tickets to the pool periodically
                ticketPool.addTickets(releaseRate);
                System.out.println("Vendor added " + releaseRate + " tickets.");

                // Sleep to simulate a delay between releasing tickets (e.g., 2 seconds)
                Thread.sleep(2000);  // 2-second interval for ticket release
            }
        } catch (InterruptedException e) {
            // Handle the interruption if the thread is stopped
            System.out.println("Vendor thread interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}

