package org.carsharingapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.carsharingapp.dto.user.UserRegistrationRequestDto;
import org.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import org.carsharingapp.exeption.RegistrationException;
import org.carsharingapp.exeption.EntityNotFoundException;
import org.carsharingapp.mapper.UserMapper;
import org.carsharingapp.model.User;
import org.carsharingapp.repository.UserRepository;
import org.carsharingapp.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {
    private final UserRepository repository = mock(UserRepository.class);
    private final UserMapper mapper = mock(UserMapper.class);
    private final PasswordEncoder encoder = mock(PasswordEncoder.class);
    private final UserServiceImpl service = new UserServiceImpl(repository, mapper, encoder);

    @Test
    void registrationEncodesPasswordAndAssignsCustomerRole() {
        UserRegistrationRequestDto request = new UserRegistrationRequestDto();
        request.setEmail("user@example.com");
        request.setPassword("password");
        User user = new User();
        user.setRole(User.Role.MANAGER);
        when(mapper.toModel(request)).thenReturn(user);
        when(encoder.encode("password")).thenReturn("encoded");
        service.register(request);
        assertEquals("encoded", user.getPassword());
        assertEquals(User.Role.CUSTOMER, user.getRole());
        verify(repository).save(user);
    }

    @Test
    void rejectsDuplicateEmail() {
        UserRegistrationRequestDto request = new UserRegistrationRequestDto();
        request.setEmail("user@example.com");
        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));
        assertThrows(RegistrationException.class, () -> service.register(request));
        verifyNoInteractions(encoder, mapper);
    }

    @Test
    void updatesRoleAndRejectsMissingUser() {
        User user = new User();
        UpdateUserRoleRequestDto request = new UpdateUserRoleRequestDto();
        request.setRoleName(User.Role.MANAGER);
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        service.updateUserRole(1L, request);
        assertEquals(User.Role.MANAGER, user.getRole());
        verify(repository).save(user);
        assertThrows(EntityNotFoundException.class, () -> service.updateUserRole(2L, request));
    }
}
