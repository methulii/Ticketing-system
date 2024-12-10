package com.example.ticketing.service;

import com.example.ticketing.model.Customer;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.repository.CustomerRepository;
import com.example.ticketing.repository.TicketRepository;
import com.example.ticketing.util.LogUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private static final Logger logger = LogUtil.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;

    public TicketService(TicketRepository ticketRepository, CustomerRepository customerRepository) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
    }

    public long countAvailableTickets() {
        logger.info("Fetching available ticket count");
        return ticketRepository.countByStatus("AVAILABLE");
    }

    public void addTickets(int count) {
        logger.info("Adding {} tickets to the pool", count);
        for (int i = 0; i < count; i++) {
            ticketRepository.save(new Ticket("AVAILABLE"));
        }
        logger.info("{} tickets added successfully", count);
    }

    public String purchaseTickets(int count, String customerEmail) {
        logger.info("Attempting to purchase {} tickets for customer: {}", count, customerEmail);
        Customer customer = customerRepository.findByEmail(customerEmail);
        if (customer == null) {
            logger.error("Customer not found with email: {}", customerEmail);
            return "Customer not found!";
        }

        List<Ticket> availableTickets = ticketRepository.findAll().stream()
                .filter(ticket -> "AVAILABLE".equals(ticket.getStatus()))
                .limit(count)
                .toList();

        if (availableTickets.size() < count) {
            logger.error("Not enough tickets available for customer: {}", customerEmail);
            return "Not enough tickets available";
        }

        for (Ticket ticket : availableTickets) {
            ticket.setStatus("PURCHASED");
            ticket.setPurchaseTime(LocalDateTime.now());
            ticket.setCustomer(customer);
            ticketRepository.save(ticket);
        }

        logger.info("Successfully purchased {} tickets for customer: {}", count, customerEmail);
        return "Successfully purchased " + count + " tickets";
    }
}
