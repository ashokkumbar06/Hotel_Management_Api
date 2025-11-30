package com.HMA.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    private String id;
    @NotBlank(message = "First name is required")
    private String name;
    private String email;
    private String password;
}