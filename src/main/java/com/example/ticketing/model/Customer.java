package com.example.ticketing.model;

import com.example.ticketing.model.Ticket;
import com.fasterxml.jackson.annotation.JsonManagedReference; // Import for managing reference
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "customers")
public class Customer {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // Manages the serialization of tickets list
    private List<Ticket> tickets;

    public Customer() {}

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
