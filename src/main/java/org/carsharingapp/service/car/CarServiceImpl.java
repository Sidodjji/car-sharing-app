package org.carsharingapp.service.car;

import lombok.RequiredArgsConstructor;
import org.carsharingapp.dto.car.CarDto;
import org.carsharingapp.dto.car.CreateCarRequestDto;
import org.carsharingapp.exeption.EntityNotFoundException;
import org.carsharingapp.mapper.CarMapper;
import org.carsharingapp.model.Car;
import org.carsharingapp.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public CarDto save(CreateCarRequestDto requestDto) {
        Car car = carMapper.toModel(requestDto);
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    public Page<CarDto> findAll(Pageable pageable) {
        return carRepository.findAll(pageable)
                .map(carMapper::toDto);
    }

    @Override
    public CarDto findCarById(Long id) {
        return carMapper.toDto(carRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find car with id: " + id)));
    }

    @Override
    public CarDto update(Long id, CreateCarRequestDto requestDto) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find car with id: " + id));
        carMapper.updateCarFromDto(requestDto, car);
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }
}
