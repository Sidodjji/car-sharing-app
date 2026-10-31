package org.carsharingapp.dto.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePaymentRequestDto(
        @NotNull
        @Positive
        Long rentalId
) {
}
