package org.carsharingapp.dto.payment;

import org.carsharingapp.model.Payment;

public record PaymentDto(
        Long id,
        Long rentalId,
        String sessionUrl,
        Long amountTotal,
        String currency,
        Payment.PaymentStatus paymentStatus
) {
}
