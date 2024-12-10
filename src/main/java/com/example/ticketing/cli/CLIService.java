package com.example.ticketing.cli;

import com.example.ticketing.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class CLIService {
    @Autowired
    private Configuration configuration;

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        while (true) {
            try {
                System.out.println("\n--- Ticketing System Configuration Menu ---");
                System.out.println("1. Modify Configuration");
                System.out.println("2. Exit");
                System.out.print("Choose an option (1-2): ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        modifyConfiguration();
                        break;
                    case 2:
                        System.out.println("Exiting... Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();  // Clear buffer
            }
        }
    }

    private void modifyConfiguration() {
        try {
            System.out.println("\nModify Configuration:");

            // Get Total Number of Tickets
            System.out.print("Enter Total Number of Tickets: ");
            configuration.setTotalTickets(scanner.nextInt());

            // Get Ticket Release Rate
            System.out.print("Enter Ticket Release Rate (tickets per minute): ");
            configuration.setTicketReleaseRate(scanner.nextInt());

            // Get Customer Retrieval Rate
            System.out.print("Enter Customer Retrieval Rate (customers per minute): ");
            configuration.setCustomerRetrievalRate(scanner.nextInt());

            // Get Maximum Ticket Capacity
            System.out.print("Enter Maximum Ticket Capacity: ");
            configuration.setMaxTicketCapacity(scanner.nextInt());

            System.out.println("Configuration updated successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter valid integers.");
            scanner.nextLine(); // Clear buffer
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid value provided: " + e.getMessage());
        }
    }
}
