package com.HMA.Mapper;

import com.HMA.DTO.BookingDTO;
import com.HMA.Entity.Booking;
import com.HMA.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingMapperTest {

    @Autowired
    private BookingMapper bookingMapper;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void shouldMapBookingToDTOWithAllFields() {
        User user = new User();
        user.setId("user1");
        user.setName("John Doe");
        user.setEmail("john@example.com");

        Booking booking = new Booking();
        booking.setId("booking1");
        booking.setUser(user);
        booking.setRoomNo(101);
        booking.setCheckIn(LocalDate.now().plusDays(1));
        booking.setCheckOut(LocalDate.now().plusDays(3));
        booking.setTotalPrice(200.0);
        booking.setStatus("CONFIRMED");

        BookingDTO mappedDTO = new BookingDTO();
        mappedDTO.setId("booking1");
        mappedDTO.setUser(user);
        mappedDTO.setRoomNo(101);
        mappedDTO.setCheckIn(LocalDate.now().plusDays(1));
        mappedDTO.setCheckOut(LocalDate.now().plusDays(3));
        mappedDTO.setTotalPrice(200.0);
        mappedDTO.setStatus("CONFIRMED");

        when(modelMapper.map(booking, BookingDTO.class)).thenReturn(mappedDTO);

        BookingDTO result = bookingMapper.toDTO(booking);

        assertNotNull(result);
        assertEquals("booking1", result.getId());
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(101, result.getRoomNo());
        assertEquals(200.0, result.getTotalPrice());
        assertEquals("CONFIRMED", result.getStatus());
    }

    @Test
    void shouldMapBookingToDTOWithNullStatus() {
        Booking booking = new Booking();
        booking.setId("booking1");
        booking.setRoomNo(101);
        booking.setTotalPrice(200.0);

        BookingDTO mappedDTO = new BookingDTO();
        mappedDTO.setId("booking1");
        mappedDTO.setRoomNo(101);
        mappedDTO.setTotalPrice(200.0);
        mappedDTO.setStatus(null);

        when(modelMapper.map(booking, BookingDTO.class)).thenReturn(mappedDTO);

        BookingDTO result = bookingMapper.toDTO(booking);

        assertNotNull(result);
        assertNull(result.getStatus());
    }

    @Test
    void shouldMapDTOToEntityWithAllFields() {
        User user = new User();
        user.setId("user1");

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId("booking1");
        bookingDTO.setUser(user);
        bookingDTO.setRoomNo(101);
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(3));
        bookingDTO.setTotalPrice(200.0);
        bookingDTO.setStatus("CONFIRMED");

        Booking mappedBooking = new Booking();
        mappedBooking.setId("booking1");
        mappedBooking.setUser(user);
        mappedBooking.setRoomNo(101);
        mappedBooking.setCheckIn(LocalDate.now().plusDays(1));
        mappedBooking.setCheckOut(LocalDate.now().plusDays(3));
        mappedBooking.setTotalPrice(200.0);
        mappedBooking.setStatus("CONFIRMED");

        when(modelMapper.map(bookingDTO, Booking.class)).thenReturn(mappedBooking);

        Booking result = bookingMapper.toEntity(bookingDTO);

        assertNotNull(result);
        assertEquals("booking1", result.getId());
        assertEquals(101, result.getRoomNo());
        assertEquals(200.0, result.getTotalPrice());
        assertEquals("CONFIRMED", result.getStatus());
    }

    @Test
    void shouldMapDTOToEntityWithNullUser() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setRoomNo(101);
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(3));
        bookingDTO.setTotalPrice(200.0);
        bookingDTO.setUser(null);

        Booking mappedBooking = new Booking();
        mappedBooking.setRoomNo(101);
        mappedBooking.setTotalPrice(200.0);
        mappedBooking.setUser(null);

        when(modelMapper.map(bookingDTO, Booking.class)).thenReturn(mappedBooking);

        Booking result = bookingMapper.toEntity(bookingDTO);

        assertNotNull(result);
        assertNull(result.getUser());
        assertEquals(101, result.getRoomNo());
    }

    @Test
    void shouldPreserveUserObjectWhenMappingToDTO() {
        User user = new User();
        user.setId("user1");
        user.setEmail("john@example.com");
        user.setName("John Doe");

        Booking booking = new Booking();
        booking.setId("booking1");
        booking.setUser(user);
        booking.setRoomNo(101);

        BookingDTO mappedDTO = new BookingDTO();
        mappedDTO.setUser(user);

        when(modelMapper.map(booking, BookingDTO.class)).thenReturn(mappedDTO);

        BookingDTO result = bookingMapper.toDTO(booking);

        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals("user1", result.getUser().getId());
    }
}