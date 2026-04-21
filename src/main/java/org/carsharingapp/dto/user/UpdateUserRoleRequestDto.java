package org.carsharingapp.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.carsharingapp.model.User;

@Data
public class UpdateUserRoleRequestDto {
    @NotNull
    private User.Role roleName;
}
