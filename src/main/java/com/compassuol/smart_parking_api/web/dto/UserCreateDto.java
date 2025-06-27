package com.compassuol.smart_parking_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDto {

    @NotBlank
    @Email(regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Invalid email format")
    private String username;
    @NotBlank
    @Size(min = 6, max = 6, message = "Size between 6 and 6")
    private String password;
}
