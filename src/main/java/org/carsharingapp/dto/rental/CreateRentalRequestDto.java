package org.carsharingapp.dto.rental;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRentalRequestDto {
    @NotNull
    private Long carId;
    @NotNull
    @Min(value = 1)
    @Max(value = 30)
    private Integer daysCount;
}
