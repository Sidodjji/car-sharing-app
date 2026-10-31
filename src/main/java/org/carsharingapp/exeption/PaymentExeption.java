package org.carsharingapp.exeption;

public class PaymentExeption extends RuntimeException {
    public PaymentExeption(String message) {
        super(message);
    }
}
