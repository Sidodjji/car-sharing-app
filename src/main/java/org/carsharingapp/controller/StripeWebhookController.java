package org.carsharingapp.controller;

import lombok.RequiredArgsConstructor;
import org.carsharingapp.service.stripe.StripeWebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class StripeWebhookController {
    private final StripeWebhookService stripeWebhookService;

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature
    ) {
        stripeWebhookService.handleEvent(payload, signature);
        return ResponseEntity.ok().build();
    }
}
