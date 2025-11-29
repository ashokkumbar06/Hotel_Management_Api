package com.HMA.Service;

import com.HMA.DTO.BookingDTO;
import com.HMA.Entity.Booking;

import java.util.List;

public interface BookingService {

    BookingDTO createBooking(BookingDTO booking);

    List<Booking> getAllBookings();

    Booking getBookingById(String id);

    Booking updateBooking(String id, Booking booking);

    void deleteBooking(String id);
}