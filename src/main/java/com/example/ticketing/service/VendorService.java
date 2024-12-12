package com.example.ticketing.service;

import java.util.HashSet;
import java.util.Set;

public class VendorService {

    // Set of registered vendor IDs
    private static final Set<String> registeredVendors = new HashSet<>();

    static {
        // Add fixed vendor IDs (can be hardcoded or loaded from config)
        registeredVendors.add("VENDOR1");
        registeredVendors.add("VENDOR2");
        registeredVendors.add("VENDOR3");
        registeredVendors.add("VENDOR4");
        registeredVendors.add("VENDOR5");
    }

    // Method to check if a vendor ID is valid
    public static boolean isValidVendor(String vendorId) {
        return registeredVendors.contains(vendorId);
    }
}
