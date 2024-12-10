package com.example.ticketing.model;

import com.example.ticketing.model.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference; // Import for breaking circular reference
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Status cannot be empty")
    @Column(nullable = false)
    private String status; // "AVAILABLE" or "PURCHASED"

    @Column(name = "purchase_time", nullable = true)
    private LocalDateTime purchaseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    @JsonBackReference  // Prevents infinite recursion while serializing Customer from Ticket
    private Customer customer;

    public Ticket() {}

    public Ticket(String status) {
        this.status = status;
    }
}

