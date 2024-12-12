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
import java.util.Set;

@Service
public class TicketService {

    private static final Logger logger = LogUtil.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;

    public TicketService(TicketRepository ticketRepository, CustomerRepository customerRepository) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
    }

    private static final Set<String> validVendorIds = Set.of("VENDOR1", "VENDOR2", "VENDOR3", "VENDOR4", "VENDOR5");

    // Validates the vendor ID
    public static boolean isValidVendor(String vendorId) {
        return isValidVendorId(vendorId);
    }

    // Checks if the vendor ID is in the list of valid vendors
    public static boolean isValidVendorId(String vendorId) {
        return validVendorIds.contains(vendorId);
    }

    public long countAvailableTickets() {
        logger.info("Fetching available ticket count");
        return ticketRepository.countByStatus("AVAILABLE");
    }

    public void addTickets(int count, String vendorId) {
        logger.info("Adding {} tickets to the pool", count);
        if (!isValidVendor(vendorId)) {
            throw new IllegalArgumentException("Invalid vendor");
        }
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
        }

        ticketRepository.saveAll(availableTickets);  // Bulk save
        logger.info("Successfully purchased {} tickets for customer: {}", count, customerEmail);
        return "Successfully purchased " + count + " tickets";
    }
}

