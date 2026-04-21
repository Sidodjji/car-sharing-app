package org.carsharingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carsharingapp.dto.user.UpdateUserRequestDto;
import org.carsharingapp.dto.user.UpdateUserRoleRequestDto;
import org.carsharingapp.dto.user.UserResponseDto;
import org.carsharingapp.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users management", description = "Endpoints for user management")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Update user role")
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRole(@PathVariable Long id,
            @Valid @RequestBody UpdateUserRoleRequestDto requestDto) {
        userService.updateUserRole(id, requestDto);
    }

    @Operation(summary = "Get current user profile")
    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @GetMapping("/me")
    public UserResponseDto findUser() {
        return userService.findUser();
    }

    @Operation(summary = "Update current user profile")
    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @PutMapping("/me")
    public void update(@Valid @RequestBody UpdateUserRequestDto requestDto) {
        userService.update(requestDto);
    }
}
