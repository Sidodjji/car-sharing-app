package org.carsharingapp.mapper;

import org.carsharingapp.config.MapperConfig;
import org.carsharingapp.dto.car.CarDto;
import org.carsharingapp.dto.car.CreateCarRequestDto;
import org.carsharingapp.dto.car.UpdateCarRequestDto;
import org.carsharingapp.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarDto toDto(Car car);

    Car toModel(CreateCarRequestDto requestDto);

    void updateCarFromDto(UpdateCarRequestDto requestDto, @MappingTarget Car car);
}
