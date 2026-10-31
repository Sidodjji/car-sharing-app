package org.carsharingapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionId;
    @Column(length = 2048)
    private String sessionUrl;
    private Long amountTotal;
    private String currency;
    private String paymentIntentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false, unique = true)
    private Rental rental;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public enum PaymentStatus {
        UNPAID,
        PAID,
        NO_PAYMENT_REQUIRED
    }
}
