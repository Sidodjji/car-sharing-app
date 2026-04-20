package org.carsharingapp.service.car;

import org.carsharingapp.dto.car.CarDto;
import org.carsharingapp.dto.car.CreateCarRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {
    CarDto save(CreateCarRequestDto requestDto);

    Page<CarDto> findAll(Pageable pageable);

    CarDto findCarById(Long id);

    CarDto update(Long id, CreateCarRequestDto requestDto);

    void deleteById(Long id);
}
