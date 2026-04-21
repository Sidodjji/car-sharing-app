package org.carsharingapp.repository;

import java.util.Optional;
import org.carsharingapp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByBrandAndModel(String brand, String model);
}
