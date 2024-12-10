package com.example.ticketing.service;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {

    private final Queue<String> tickets = new LinkedList<>();

    @Setter
    @Getter
    private int maxCapacity;

    // Constructor
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Add tickets to the pool
    public synchronized void addTickets(int count) {
        while (tickets.size() + count > maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        for (int i = 0; i < count; i++) {
            tickets.add("Ticket " + (tickets.size() + 1));
        }
        System.out.println(count + " tickets added. Total: " + tickets.size());
        notifyAll();
    }

    // Retrieve a ticket from the pool
    public synchronized String retrieveTicket() {
        while (tickets.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String ticket = tickets.poll();
        System.out.println("Ticket retrieved: " + ticket);
        notifyAll();
        return ticket;
    }
}
