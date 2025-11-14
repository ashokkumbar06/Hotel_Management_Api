package com.HMA.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "Booking_data")
public class Booking {

    @Id
    private String id;

    private Room room;

    private User user;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalPrice;

    private String status = "CONFIRMED"; // CANCELLED, COMPLETED
}