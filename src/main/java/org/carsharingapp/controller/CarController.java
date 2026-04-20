package org.carsharingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carsharingapp.dto.car.CarDto;
import org.carsharingapp.dto.car.CreateCarRequestDto;
import org.carsharingapp.service.car.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
@Tag(name = "Cars management", description = "Endpoints for car authentication")
public class CarController {
    private final CarService carService;

    @Operation(summary = "Get all cars with pagination")
    @GetMapping
    public Page<CarDto> getAll(Pageable pageable) {
        return carService.findAll(pageable);
    }

    @Operation(summary = "Create a new car")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto createBook(@RequestBody @Valid CreateCarRequestDto requestDto) {
        return carService.save(requestDto);
    }

    @Operation(summary = "Get car by id")
    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        return carService.findCarById(id);
    }

    @Operation(summary = "Update car by id")
    @PutMapping("/{id}")
    public CarDto updateBook(@PathVariable @Valid Long id,
                              @RequestBody CreateCarRequestDto requestDto) {
        return carService.update(id, requestDto);
    }

    @Operation(summary = "Delete car by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carService.deleteById(id);
    }
}
