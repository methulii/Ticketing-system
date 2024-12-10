package com.example.ticketing.controller;

import com.example.ticketing.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {

    private final TicketService ticketService;
    private static final Logger logger = Logger.getLogger(TicketController.class.getName());

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Get available tickets
    @GetMapping("/available")
    public long getAvailableTickets() {
        try {
            return ticketService.countAvailableTickets();
        } catch (Exception e) {
            logger.severe("Error occurred while getting available tickets: " + e.getMessage());
            throw new RuntimeException("Unable to fetch available tickets");
        }
    }

    // Add tickets to the pool
    @PostMapping("/add")
    public String addTickets(@RequestBody TicketAddRequest request) {
        try {
            ticketService.addTickets(request.getCount());
            return request.getCount() + " tickets added successfully!";
        } catch (Exception e) {
            logger.severe("Error occurred while adding tickets: " + e.getMessage());
            throw new RuntimeException("Unable to add tickets");
        }
    }

    // Purchase tickets for a customer
    @PostMapping("/purchase")
    public String purchaseTickets(@RequestBody TicketPurchaseRequest request) {
        try {
            return ticketService.purchaseTickets(request.getCount(), request.getCustomerEmail());
        } catch (Exception e) {
            logger.severe("Error occurred while purchasing tickets for " + request.getCustomerEmail() + ": " + e.getMessage());
            throw new RuntimeException("Unable to process ticket purchase");
        }
    }
}

