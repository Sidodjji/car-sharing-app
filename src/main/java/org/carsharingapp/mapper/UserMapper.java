package org.carsharingapp.mapper;

import org.carsharingapp.config.MapperConfig;
import org.carsharingapp.dto.user.UpdateUserRequestDto;
import org.carsharingapp.dto.user.UserRegistrationRequestDto;
import org.carsharingapp.dto.user.UserResponseDto;
import org.carsharingapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);

    void updateUserFromDto(UpdateUserRequestDto dto, @MappingTarget User user);
}
