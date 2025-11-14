package com.HMA.Service;

import com.HMA.Entity.Booking;
import com.HMA.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public Booking updateBooking(String id, Booking booking) {
        Booking existing = getBookingById(id);
        existing.setRoom(booking.getRoom());
        existing.setUser(booking.getUser());
        existing.setCheckIn(booking.getCheckIn());
        existing.setCheckOut(booking.getCheckOut());
        existing.setTotalPrice(booking.getTotalPrice());
        existing.setStatus(booking.getStatus());
        return bookingRepository.save(existing);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
}