package org.carsharingapp.repository;

import java.time.LocalDate;
import java.util.List;
import org.carsharingapp.model.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    Page<Rental> findByUserId(Long id, Pageable pageable);

    Page<Rental> findByUserIdAndActiveTrue(Long id, Pageable pageable);

    Page<Rental> findByUserIdAndActiveFalse(Long id, Pageable pageable);

    List<Rental> findByActiveTrueAndReturnDateLessThanEqual(LocalDate returnDate);
}
