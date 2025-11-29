package com.HMA.Mapper;

import com.HMA.DTO.UserDto;
import com.HMA.Entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapping {

    @Autowired
    private ModelMapper modelMapper;

    public UserDto toDTO(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }

    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }
}