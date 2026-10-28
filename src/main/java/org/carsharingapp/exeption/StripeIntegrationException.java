package org.carsharingapp.exeption;

public class StripeIntegrationException extends RuntimeException {
    public StripeIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
