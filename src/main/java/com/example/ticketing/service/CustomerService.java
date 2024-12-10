package com.example.ticketing.service;

import com.example.ticketing.model.Customer;
import com.example.ticketing.repository.CustomerRepository;
import com.example.ticketing.util.LogUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private static final Logger logger = LogUtil.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Add a new customer
    public Customer addCustomer(String name, String email) {
        logger.info("Attempting to add a new customer with name: {} and email: {}", name, email);
        try {
            if (customerRepository.findByEmail(email) != null) {
                logger.warn("Customer with email {} already exists.", email);
                return null;  // Email already exists
            }
            Customer customer = new Customer(name, email);
            customerRepository.save(customer);
            logger.info("Customer with email {} added successfully.", email);
            return customer;
        } catch (Exception e) {
            logger.error("Error occurred while adding customer with email {}: {}", email, e.getMessage());
            throw new RuntimeException("Failed to add customer", e);  // Rethrow or handle as needed
        }
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        logger.info("Fetching all customers.");
        try {
            List<Customer> customers = customerRepository.findAll();
            logger.info("Fetched {} customers.", customers.size());
            return customers;
        } catch (Exception e) {
            logger.error("Error occurred while fetching all customers: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch customers", e);
        }
    }

    // Find customer by email
    public Customer findCustomerByEmail(String email) {
        logger.info("Searching for customer with email: {}", email);
        try {
            Customer customer = customerRepository.findByEmail(email);
            if (customer == null) {
                logger.warn("No customer found with email: {}", email);
            } else {
                logger.info("Customer found with email: {}", email);
            }
            return customer;
        } catch (Exception e) {
            logger.error("Error occurred while searching for customer with email {}: {}", email, e.getMessage());
            throw new RuntimeException("Failed to find customer", e);
        }
    }

    // Update existing customer
    public void updateCustomer(Customer existingCustomer) {
        logger.info("Attempting to update customer with ID: {}", existingCustomer.getId());
        try {
            customerRepository.save(existingCustomer);
            logger.info("Customer with ID: {} updated successfully.", existingCustomer.getId());
        } catch (Exception e) {
            logger.error("Error occurred while updating customer with ID {}: {}", existingCustomer.getId(), e.getMessage());
            throw new RuntimeException("Failed to update customer", e);
        }
    }

    // Delete customer by email
    public boolean deleteCustomer(String email) {
        logger.info("Attempting to delete customer with email: {}", email);
        try {
            Customer customer = customerRepository.findByEmail(email);
            if (customer != null) {
                customerRepository.delete(customer);
                logger.info("Customer with email {} deleted successfully.", email);
                return true;
            }
            logger.warn("Customer with email {} not found for deletion.", email);
            return false;
        } catch (Exception e) {
            logger.error("Error occurred while deleting customer with email {}: {}", email, e.getMessage());
            throw new RuntimeException("Failed to delete customer", e);
        }
    }
}

