package com.HMA.DTO;

import com.HMA.Entity.User;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingDTO {
    private String id;
    private User user;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalPrice;
    private String status;
}