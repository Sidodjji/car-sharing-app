package org.carsharingapp.service.rental;

import org.carsharingapp.dto.rental.CreateRentalRequestDto;
import org.carsharingapp.dto.rental.RentalDto;
import org.carsharingapp.dto.rental.RentalDtoWithActualReturnDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentalService {
    RentalDto save(CreateRentalRequestDto requestDto);

    Page<RentalDtoWithActualReturnDate> findRentalIsActive(
            Long id,
            boolean isActive,
            Pageable pageable);

    RentalDto findById(Long id);

    RentalDtoWithActualReturnDate returnCar(Long rentalId);
}
