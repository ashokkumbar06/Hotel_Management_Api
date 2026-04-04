package com.HMA.Service;

import com.HMA.DTO.BookingDTO;
import com.HMA.Entity.Booking;
import com.HMA.Entity.User;
import com.HMA.Exception.ResourceNotFoundException;
import com.HMA.Mapper.BookingMapper;
import com.HMA.Repository.BookingRepository;
import com.HMA.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class BookingServiceImplTest {

    @Autowired
    private BookingServiceImpl bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookingMapper bookingMapper;

    @Test
    void shouldCreateBookingWhenValidDataProvided() {
        User user = new User();
        user.setId("user1");
        user.setEmail("john@example.com");

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUser(user);
        bookingDTO.setRoomNo(101);
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(3));
        bookingDTO.setTotalPrice(200.0);
        bookingDTO.setStatus("CONFIRMED");

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoomNo(101);
        booking.setCheckIn(LocalDate.now().plusDays(1));
        booking.setCheckOut(LocalDate.now().plusDays(3));
        booking.setTotalPrice(200.0);
        booking.setStatus("CONFIRMED");

        Booking savedBooking = new Booking();
        savedBooking.setId("booking1");
        savedBooking.setUser(user);
        savedBooking.setRoomNo(101);
        savedBooking.setCheckIn(LocalDate.now().plusDays(1));
        savedBooking.setCheckOut(LocalDate.now().plusDays(3));
        savedBooking.setTotalPrice(200.0);
        savedBooking.setStatus("CONFIRMED");

        BookingDTO expectedDTO = new BookingDTO();
        expectedDTO.setId("booking1");
        expectedDTO.setUser(user);
        expectedDTO.setRoomNo(101);
        expectedDTO.setCheckIn(LocalDate.now().plusDays(1));
        expectedDTO.setCheckOut(LocalDate.now().plusDays(3));
        expectedDTO.setTotalPrice(200.0);
        expectedDTO.setStatus("CONFIRMED");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of("john@example.com"));
        when(bookingMapper.toEntity(bookingDTO)).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(expectedDTO);

        BookingDTO result = bookingService.createBooking(bookingDTO);

        assertNotNull(result);
        assertEquals("booking1", result.getId());
        assertEquals(101, result.getRoomNo());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenRoomNumberIsZero() {
        User user = new User();
        user.setEmail("john@example.com");

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUser(user);
        bookingDTO.setRoomNo(0);
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(3));
        bookingDTO.setTotalPrice(200.0);

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of("john@example.com"));

        assertThrows(ResourceNotFoundException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @Test
    void shouldNotThrowExceptionWhenCheckOutAfterCheckIn() {
        User user = new User();
        user.setId("user1");
        user.setEmail("john@example.com");

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUser(user);
        bookingDTO.setRoomNo(101);
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(3));
        bookingDTO.setTotalPrice(200.0);
        bookingDTO.setStatus("CONFIRMED");

        Booking booking = new Booking();
        Booking savedBooking = new Booking();
        savedBooking.setId("booking1");

        BookingDTO expectedDTO = new BookingDTO();
        expectedDTO.setId("booking1");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of("john@example.com"));
        when(bookingMapper.toEntity(bookingDTO)).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(expectedDTO);

        BookingDTO result = bookingService.createBooking(bookingDTO);

        assertNotNull(result);
    }

    @Test
    void shouldNotThrowExceptionWhenUserEmailExists() {
        User user = new User();
        user.setId("user1");
        user.setEmail("john@example.com");

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUser(user);
        bookingDTO.setRoomNo(101);
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(3));
        bookingDTO.setTotalPrice(200.0);

        Booking booking = new Booking();
        Booking savedBooking = new Booking();
        savedBooking.setId("booking1");

        BookingDTO expectedDTO = new BookingDTO();
        expectedDTO.setId("booking1");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of("john@example.com"));
        when(bookingMapper.toEntity(bookingDTO)).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(expectedDTO);

        BookingDTO result = bookingService.createBooking(bookingDTO);

        assertNotNull(result);
    }

    @Test
    void shouldReturnAllBookings() {
        Booking booking1 = new Booking();
        booking1.setId("booking1");
        booking1.setRoomNo(101);

        Booking booking2 = new Booking();
        booking2.setId("booking2");
        booking2.setRoomNo(102);

        List<Booking> bookings = Arrays.asList(booking1, booking2);
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookings();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("booking1", result.getFirst().getId());
    }

    @Test
    void shouldReturnEmptyListWhenNoBookingsExist() {
        when(bookingRepository.findAll()).thenReturn(List.of());

        List<Booking> result = bookingService.getAllBookings();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnBookingWhenIdExists() {
        Booking booking = new Booking();
        booking.setId("booking1");
        booking.setRoomNo(101);

        when(bookingRepository.findById("booking1")).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingById("booking1");

        assertNotNull(result);
        assertEquals("booking1", result.getId());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenBookingIdNotFound() {
        when(bookingRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.getBookingById("nonexistent"));
    }

    @Test
    void shouldUpdateBookingWhenIdExists() {
        Booking existingBooking = new Booking();
        existingBooking.setId("booking1");
        existingBooking.setCheckIn(LocalDate.now().plusDays(1));
        existingBooking.setCheckOut(LocalDate.now().plusDays(3));
        existingBooking.setTotalPrice(200.0);
        existingBooking.setStatus("CONFIRMED");

        BookingDTO updateDTO = new BookingDTO();
        updateDTO.setCheckIn(LocalDate.now().plusDays(2));
        updateDTO.setCheckOut(LocalDate.now().plusDays(5));
        updateDTO.setTotalPrice(300.0);
        updateDTO.setStatus("CONFIRMED");

        Booking mappedBooking = new Booking();
        mappedBooking.setCheckIn(LocalDate.now().plusDays(2));
        mappedBooking.setCheckOut(LocalDate.now().plusDays(5));
        mappedBooking.setTotalPrice(300.0);
        mappedBooking.setStatus("CONFIRMED");

        Booking updatedBooking = new Booking();
        updatedBooking.setId("booking1");
        updatedBooking.setCheckIn(LocalDate.now().plusDays(2));
        updatedBooking.setCheckOut(LocalDate.now().plusDays(5));
        updatedBooking.setTotalPrice(300.0);
        updatedBooking.setStatus("CONFIRMED");

        BookingDTO resultDTO = new BookingDTO();
        resultDTO.setId("booking1");
        resultDTO.setCheckIn(LocalDate.now().plusDays(2));
        resultDTO.setCheckOut(LocalDate.now().plusDays(5));
        resultDTO.setTotalPrice(300.0);
        resultDTO.setStatus("CONFIRMED");

        when(bookingMapper.toEntity(updateDTO)).thenReturn(mappedBooking);
        when(bookingRepository.findById("booking1")).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(updatedBooking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(resultDTO);

        BookingDTO result = bookingService.updateBooking("booking1", updateDTO);

        assertNotNull(result);
        assertEquals("booking1", result.getId());
        assertEquals(300.0, result.getTotalPrice());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenUpdatingNonExistentBooking() {
        BookingDTO updateDTO = new BookingDTO();
        updateDTO.setCheckIn(LocalDate.now().plusDays(2));
        updateDTO.setCheckOut(LocalDate.now().plusDays(5));

        Booking mappedBooking = new Booking();

        when(bookingMapper.toEntity(updateDTO)).thenReturn(mappedBooking);
        when(bookingRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.updateBooking("nonexistent", updateDTO));
    }

    @Test
    void shouldDeleteBookingWhenIdExists() {
        bookingService.deleteBooking("booking1");

        verify(bookingRepository).deleteById("booking1");
    }
}