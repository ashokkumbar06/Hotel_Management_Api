package com.HMA.Controller;

import com.HMA.DTO.BookingDTO;
import com.HMA.Entity.Booking;
import com.HMA.Entity.User;
import com.HMA.Exception.ResourceNotFoundException;
import com.HMA.Service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateBookingWhenValidDataProvided() throws Exception {
        User user = new User();
        user.setId("user1");
        user.setName("John Doe");
        user.setEmail("john@example.com");

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUser(user);
        bookingDTO.setRoomNo(101);
        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
        bookingDTO.setCheckOut(LocalDate.now().plusDays(3));
        bookingDTO.setTotalPrice(200.0);
        bookingDTO.setStatus("CONFIRMED");

        BookingDTO createdBooking = new BookingDTO();
        createdBooking.setId("booking1");
        createdBooking.setUser(user);
        createdBooking.setRoomNo(101);
        createdBooking.setCheckIn(LocalDate.now().plusDays(1));
        createdBooking.setCheckOut(LocalDate.now().plusDays(3));
        createdBooking.setTotalPrice(200.0);
        createdBooking.setStatus("CONFIRMED");

        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(createdBooking);

        mockMvc.perform(post("/api/v1/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("booking1"))
                .andExpect(jsonPath("$.roomNo").value(101));
    }

    @Test
    void shouldReturnAllBookings() throws Exception {
        User user = new User();
        user.setId("user1");
        user.setName("John Doe");

        Booking booking1 = new Booking();
        booking1.setId("booking1");
        booking1.setUser(user);
        booking1.setRoomNo(101);
        booking1.setCheckIn(LocalDate.now().plusDays(1));
        booking1.setCheckOut(LocalDate.now().plusDays(3));
        booking1.setTotalPrice(200.0);
        booking1.setStatus("CONFIRMED");

        Booking booking2 = new Booking();
        booking2.setId("booking2");
        booking2.setUser(user);
        booking2.setRoomNo(102);
        booking2.setCheckIn(LocalDate.now().plusDays(2));
        booking2.setCheckOut(LocalDate.now().plusDays(4));
        booking2.setTotalPrice(250.0);
        booking2.setStatus("COMPLETED");

        List<Booking> bookings = Arrays.asList(booking1, booking2);

        when(bookingService.getAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/api/v1/booking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("booking1"))
                .andExpect(jsonPath("$[1].id").value("booking2"));
    }

    @Test
    void shouldReturnBookingWhenIdExists() throws Exception {
        User user = new User();
        user.setId("user1");
        user.setName("John Doe");

        Booking booking = new Booking();
        booking.setId("booking1");
        booking.setUser(user);
        booking.setRoomNo(101);
        booking.setCheckIn(LocalDate.now().plusDays(1));
        booking.setCheckOut(LocalDate.now().plusDays(3));
        booking.setTotalPrice(200.0);
        booking.setStatus("CONFIRMED");

        when(bookingService.getBookingById("booking1")).thenReturn(booking);

        mockMvc.perform(get("/api/v1/booking/booking1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("booking1"))
                .andExpect(jsonPath("$.roomNo").value(101));
    }

    @Test
    void shouldReturnNotFoundWhenBookingIdDoesNotExist() throws Exception {
        when(bookingService.getBookingById("nonexistent")).thenThrow(new ResourceNotFoundException("Booking not found"));

        mockMvc.perform(get("/api/v1/booking/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void shouldUpdateBookingWhenValidDataProvided() throws Exception {
        User user = new User();
        user.setId("user1");
        user.setName("John Doe");

        BookingDTO updateDTO = new BookingDTO();
        updateDTO.setUser(user);
        updateDTO.setRoomNo(102);
        updateDTO.setCheckIn(LocalDate.now().plusDays(1));
        updateDTO.setCheckOut(LocalDate.now().plusDays(3));
        updateDTO.setTotalPrice(250.0);
        updateDTO.setStatus("COMPLETED");

        BookingDTO updatedBooking = new BookingDTO();
        updatedBooking.setId("booking1");
        updatedBooking.setUser(user);
        updatedBooking.setRoomNo(102);
        updatedBooking.setCheckIn(LocalDate.now().plusDays(1));
        updatedBooking.setCheckOut(LocalDate.now().plusDays(3));
        updatedBooking.setTotalPrice(250.0);
        updatedBooking.setStatus("COMPLETED");

        when(bookingService.updateBooking(eq("booking1"), any(BookingDTO.class))).thenReturn(updatedBooking);

        mockMvc.perform(put("/api/v1/booking/booking1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("booking1"))
                .andExpect(jsonPath("$.roomNo").value(102))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonexistentBooking() throws Exception {
        BookingDTO updateDTO = new BookingDTO();
        // minimal data
        updateDTO.setRoomNo(101);

        when(bookingService.updateBooking(eq("nonexistent"), any(BookingDTO.class))).thenThrow(new ResourceNotFoundException("Booking not found"));

        mockMvc.perform(put("/api/v1/booking/nonexistent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldDeleteBookingWhenIdExists() throws Exception {
        doNothing().when(bookingService).deleteBooking("booking1");

        mockMvc.perform(delete("/api/v1/booking/booking1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking deleted successfully"));
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonexistentBooking() throws Exception {
        doThrow(new ResourceNotFoundException("Booking not found")).when(bookingService).deleteBooking("nonexistent");

        mockMvc.perform(delete("/api/v1/booking/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}