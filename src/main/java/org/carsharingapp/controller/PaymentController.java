package org.carsharingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carsharingapp.dto.payment.CreatePaymentRequestDto;
import org.carsharingapp.dto.payment.PaymentDto;
import org.carsharingapp.service.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/cancel")
    public String cancelPayment() {
        return "Payment was not completed. You can pay later using the same "
                + "payment link. The session is available for 24 hours "
                + "from the time it was created.";
    }
}
