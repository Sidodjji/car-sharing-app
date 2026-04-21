package org.carsharingapp.repository;

import org.carsharingapp.model.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    Page<Rental> findByUserId(Long id, Pageable pageable);

    Page<Rental> findByUserIdAndActualReturnDateIsNull(Long id, Pageable pageable);

    Page<Rental> findByUserIdAndActualReturnDateIsNotNull(Long id, Pageable pageable);
}
