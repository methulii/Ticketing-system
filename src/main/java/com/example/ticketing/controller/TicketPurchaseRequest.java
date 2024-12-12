package com.example.ticketing.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TicketPurchaseRequest {
    private int count;
    private String customerEmail;

}
