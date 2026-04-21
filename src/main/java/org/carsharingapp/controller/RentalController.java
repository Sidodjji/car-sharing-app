package org.carsharingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carsharingapp.dto.rental.CreateRentalRequestDto;
import org.carsharingapp.dto.rental.RentalDto;
import org.carsharingapp.dto.rental.RentalDtoWithActualReturnDate;
import org.carsharingapp.service.rental.RentalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
@Tag(name = "Rental management", description = "Endpoints for rentals management")
public class RentalController {
    private final RentalService rentalService;

    @Operation(description = "Add new rental")
    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @PostMapping
    public RentalDto createNewRental(@Valid @RequestBody CreateRentalRequestDto requestDto) {
        return rentalService.save(requestDto);
    }

    @Operation(description = "Find all active/not active users rentals ")
    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @GetMapping
    public Page<RentalDtoWithActualReturnDate> findAllSpecificRentals(
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(name = "is_active") boolean isActive,
            Pageable pageable) {
        return rentalService.findRentalIsActive(userId, isActive, pageable);
    }

    @Operation(description = "Find rental by id")
    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @GetMapping("/{id}")
    public RentalDto findById(@PathVariable Long id) {
        return rentalService.findById(id);
    }

    @Operation(description = "Return car")
    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @PostMapping("/{id}/return")
    public RentalDtoWithActualReturnDate returnCar(@PathVariable Long id) {
        return rentalService.returnCar(id);
    }
}
