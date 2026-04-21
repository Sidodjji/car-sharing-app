package org.carsharingapp.service.user;

import org.carsharingapp.dto.user.UpdateUserRequestDto;
import org.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import org.carsharingapp.dto.user.UserRegistrationRequestDto;
import org.carsharingapp.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    void update(UpdateUserRequestDto requestDto);

    UserResponseDto findUser();

    void updateUserRole(Long id, UpdateUserRoleRequestDto requestDto);
}
