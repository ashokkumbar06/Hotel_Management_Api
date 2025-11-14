package com.HMA.Service;

import com.HMA.Entity.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    List<Booking> getAllBookings();

    Booking getBookingById(String id);

    Booking updateBooking(String id, Booking booking);

    void deleteBooking(String id);
}
