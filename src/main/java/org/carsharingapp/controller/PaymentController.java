package org.carsharingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carsharingapp.dto.payment.CreatePaymentRequestDto;
import org.carsharingapp.dto.payment.PaymentDto;
import org.carsharingapp.service.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto createPayment(
            @Valid @RequestBody CreatePaymentRequestDto requestDto
    ) {
        return paymentService.createPayment(requestDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    public PaymentDto getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }
}