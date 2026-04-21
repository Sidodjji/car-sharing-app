package org.carsharingapp.mapper;

import org.carsharingapp.config.MapperConfig;
import org.carsharingapp.dto.rental.CreateRentalRequestDto;
import org.carsharingapp.dto.rental.RentalDto;
import org.carsharingapp.dto.rental.RentalDtoWithActualReturnDate;
import org.carsharingapp.model.Rental;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface RentalMapper {
    Rental toModel(CreateRentalRequestDto requestDto);

    RentalDto toDto(Rental rental);

    RentalDtoWithActualReturnDate toDtoWihActualReturnDate(Rental rental);
}
