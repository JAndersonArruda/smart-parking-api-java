package com.compassuol.smart_parking_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDto {

    @NotBlank
    @Size(min = 6, max = 6, message = "Size between 6 and 6")
    private String currentPassword;
    @NotBlank
    @Size(min = 6, max = 6, message = "Size between 6 and 6")
    private String newPassword;
    @NotBlank
    @Size(min = 6, max = 6, message = "Size between 6 and 6")
    private String confirmPassword;
}
