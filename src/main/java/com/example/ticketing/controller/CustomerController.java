package com.example.ticketing.controller;

import com.example.ticketing.model.Customer;
import com.example.ticketing.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Add a new customer
    @PostMapping("/add")
    public String addCustomer(@RequestBody Customer customer) {
        // Check if the customer already exists by email
        Customer existingCustomer = customerService.findCustomerByEmail(customer.getEmail());
        if (existingCustomer != null) {
            return "Customer with this email already exists!";
        }
        customerService.addCustomer(customer.getName(), customer.getEmail());  // Add customer
        return "Customer added successfully!";
    }

    // Find customer by email
    @PostMapping("/find")
    public String findCustomer(@RequestBody Customer customer) {
        try {
            Customer foundCustomer = customerService.findCustomerByEmail(customer.getEmail());
            if (foundCustomer == null) {
                return "Customer not found!";
            }
            return "Customer exists";
        } catch (Exception e) {
            return "Error occurred while finding customer: " + e.getMessage();
        }
    }

    // Get all customers
    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Update customer name
    @PutMapping("/update")
    public String updateCustomer(@RequestBody Customer customer) {
        try {
            Customer existingCustomer = customerService.findCustomerByEmail(customer.getEmail());
            if (existingCustomer == null) {
                return "Customer not found!";
            }
            existingCustomer.setName(customer.getName());
            customerService.updateCustomer(existingCustomer);
            return "Customer updated successfully!";
        } catch (Exception e) {
            return "Error occurred while updating customer: " + e.getMessage();
        }
    }

    // Delete customer by email
    @DeleteMapping("/delete")
    public String deleteCustomer(@RequestBody Customer customer) {
        try {
            boolean result = customerService.deleteCustomer(customer.getEmail());
            if (result) {
                return "Customer deleted successfully!";
            } else {
                return "Customer not found!";
            }
        } catch (Exception e) {
            return "Error occurred while deleting customer: " + e.getMessage();
        }
    }
}

