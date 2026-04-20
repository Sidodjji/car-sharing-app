package org.carsharingapp.dto.car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import org.carsharingapp.model.Car;

@Data
public class CreateCarRequestDto {
    @NotBlank
    private String model;
    @NotBlank
    private String brand;
    @NotNull
    private Car.Type type;
    @NotNull
    private Integer inventory;
    @NotNull
    private BigDecimal dailyFee;
}
