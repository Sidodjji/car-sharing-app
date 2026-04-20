package org.carsharingapp.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.carsharingapp.dto.user.UserRegistrationRequestDto;
import org.carsharingapp.dto.user.UserResponseDto;
import org.carsharingapp.exeption.RegistrationException;
import org.carsharingapp.mapper.UserMapper;
import org.carsharingapp.model.Role;
import org.carsharingapp.model.User;
import org.carsharingapp.repository.RoleRepository;
import org.carsharingapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role userRole = roleRepository.findByRole(Role.RoleName.CUSTOMER).orElseThrow(
                () -> new RuntimeException("Role USER not found"));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
