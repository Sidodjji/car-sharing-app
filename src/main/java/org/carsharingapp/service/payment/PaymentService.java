package org.carsharingapp.service.payment;

import org.carsharingapp.dto.payment.CreatePaymentRequestDto;
import org.carsharingapp.dto.payment.PaymentDto;

public interface PaymentService {
    PaymentDto createPayment(CreatePaymentRequestDto requestDto);

    PaymentDto getPaymentById(Long id);
}
