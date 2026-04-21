package org.carsharingapp.dto.car;

import java.math.BigDecimal;
import lombok.Data;
import org.carsharingapp.model.Car;

@Data
public class UpdateCarRequestDto {
    private String model;
    private String brand;
    private Car.Type type;
    private Integer inventory;
    private BigDecimal dailyFee;
}
