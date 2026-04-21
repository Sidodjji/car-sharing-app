package org.carsharingapp.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @Email
    private String email;
    private String firstName;
    private String lastName;
}
