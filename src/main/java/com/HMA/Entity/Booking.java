package com.HMA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Room room;

    private User user;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalPrice;

    private String status = "CONFIRMED"; // CANCELLED, COMPLETED
}