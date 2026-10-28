package org.carsharingapp.repository;

import org.carsharingapp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByRental_Id(Long rentalId);
}
