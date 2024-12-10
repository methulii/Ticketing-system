package com.example.ticketing.util;

import com.example.ticketing.config.Configuration;
import com.example.ticketing.model.Customer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.List;

public class FileUtil {

    private static final Gson gson = new Gson();

    // Save configuration to a file
    public static void saveConfiguration(Configuration configuration) throws IOException {
        try (FileWriter writer = new FileWriter("config.json")) {
            gson.toJson(configuration, writer); // Convert the configuration to JSON and write it
        }
    }

    // Load configuration from a file
    public static Configuration loadConfiguration() throws IOException {
        File file = new File("config.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                return gson.fromJson(reader, Configuration.class); // Parse the JSON file into a Configuration object
            } catch (JsonSyntaxException e) {
                System.out.println("Failed to parse configuration: " + e.getMessage());
                return null; // Return null if there's an error in parsing
            }
        } else {
            System.out.println("Configuration file not found.");
            return null; // Return null if the file doesn't exist
        }
    }

    // Save list of customers to a JSON file
    public static void saveCustomers(List<Customer> customers) throws IOException {
        try (FileWriter writer = new FileWriter("customers.json")) {
            gson.toJson(customers, writer); // Convert the list of customers to JSON and write it to the file
        }
    }

    // Load list of customers from a JSON file
    public static List<Customer> loadCustomers() throws IOException {
        File file = new File("customers.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Customer[] customerArray = gson.fromJson(reader, Customer[].class); // Convert JSON back to an array of customers
                return List.of(customerArray); // Convert array to list and return
            } catch (JsonSyntaxException e) {
                System.out.println("Failed to parse customers data: " + e.getMessage());
                return null; // Return null if there's an error in parsing
            }
        } else {
            System.out.println("Customers file not found.");
            return null; // Return null if the file doesn't exist
        }
    }
}



