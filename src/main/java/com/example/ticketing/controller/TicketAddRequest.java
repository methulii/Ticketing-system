package com.example.ticketing.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TicketAddRequest {
    private int count;
    private String vendorId;  // Add vendorId field

}
