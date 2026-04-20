package org.carsharingapp.dto.user;

import java.util.Set;
import lombok.Data;
import org.carsharingapp.model.Role;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
