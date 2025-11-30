package com.HMA.Service;

import com.HMA.DTO.BookingDTO;
import com.HMA.Entity.Booking;
import com.HMA.Mapper.BookingMapper;
import com.HMA.Repository.BookingRepository;
import com.HMA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    public BookingDTO createBooking(BookingDTO dto) {
        Optional<String> email = userRepository.findByEmail(dto.getUser().getEmail());
        if (email.isEmpty() || email.equals(null)) {
            new NullPointerException("User not found. please create new user profile for booking rooms");
        }
        if (dto.getCheckOut().isBefore(dto.getCheckIn())) {
            new IllegalArgumentException("Check-out date must be after check-in date");
        }
        Booking booking = bookingMapper.toEntity(dto);
        bookingRepository.save(booking);
        BookingDTO postData = bookingMapper.toDTO(booking);
        return postData;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public BookingDTO updateBooking(String id, BookingDTO booking) {
        Booking dto = bookingMapper.toEntity(booking);
        Booking existingDto = getBookingById(id);
        existingDto.setCheckIn(dto.getCheckIn());
        existingDto.setCheckOut(dto.getCheckOut());
        existingDto.setTotalPrice(dto.getTotalPrice());
        existingDto.setStatus(dto.getStatus());
        bookingRepository.save(existingDto);
        BookingDTO postData = bookingMapper.toDTO(existingDto);
        return postData;
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
}