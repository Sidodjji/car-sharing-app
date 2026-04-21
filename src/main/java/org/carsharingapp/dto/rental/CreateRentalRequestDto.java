package org.carsharingapp.dto.rental;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRentalRequestDto {
    @NotNull
    private Long carId;
}
