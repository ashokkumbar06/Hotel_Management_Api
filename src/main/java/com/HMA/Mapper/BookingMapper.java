package com.HMA.Mapper;

import com.HMA.DTO.BookingDTO;
import com.HMA.Entity.Booking;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    @Autowired
    private ModelMapper modelMapper;

    public BookingDTO toDTO(Booking booking) {
        BookingDTO dto = modelMapper.map(booking, BookingDTO.class);
        dto.setId(booking.getId());
        dto.setUser(booking.getUser());
        dto.setRoomNo(booking.getRoomNo());
        dto.setCheckIn(booking.getCheckIn());
        dto.setCheckOut(booking.getCheckOut());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus());
        return dto;
    }

    public Booking toEntity(BookingDTO dto) {
        return modelMapper.map(dto, Booking.class);
    }
}