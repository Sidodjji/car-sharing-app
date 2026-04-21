package org.carsharingapp.service.rental;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.carsharingapp.dto.rental.CreateRentalRequestDto;
import org.carsharingapp.dto.rental.RentalDto;
import org.carsharingapp.dto.rental.RentalDtoWithActualReturnDate;
import org.carsharingapp.exeption.EntityNotFoundException;
import org.carsharingapp.mapper.RentalMapper;
import org.carsharingapp.model.Car;
import org.carsharingapp.model.Rental;
import org.carsharingapp.model.User;
import org.carsharingapp.repository.CarRepository;
import org.carsharingapp.repository.RentalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final CarRepository carRepository;

    @Override
    public RentalDto save(CreateRentalRequestDto requestDto) {
        User user = getAuthenticatedUser();
        Rental rental = rentalMapper.toModel(requestDto);
        rental.setUserId(user.getId());
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now().plusDays(1));
        Car car = carRepository.findById(rental.getCarId()).orElseThrow(() ->
                new EntityNotFoundException("Can't find car with id: " + rental.getCarId()));
        car.setInventory(car.getInventory() - 1);
        carRepository.save(car);
        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    @Override
    public Page<RentalDtoWithActualReturnDate> findRentalIsActive(
            Long userId,
            boolean isActive,
            Pageable pageable) {
        Long resolvedUserId = resolveRequestedUserId(userId);
        if (isActive) {
            return rentalRepository.findByUserIdAndActualReturnDateIsNull(resolvedUserId, pageable)
                    .map(rentalMapper::toDtoWihActualReturnDate);
        }
        return rentalRepository.findByUserIdAndActualReturnDateIsNotNull(resolvedUserId, pageable)
                .map(rentalMapper::toDtoWihActualReturnDate);
    }

    @Override
    public RentalDto findById(Long id) {
        return rentalMapper.toDto(getAccessibleRental(id));
    }

    @Override
    public RentalDtoWithActualReturnDate returnCar(Long rentalId) {
        Rental rental = getAccessibleRental(rentalId);
        rental.setActualReturnDate(LocalDate.now());
        Car car = carRepository.findById(rental.getCarId()).orElseThrow(() ->
                new EntityNotFoundException("Can't find car with id: " + rental.getCarId()));
        car.setInventory(car.getInventory() + 1);
        carRepository.save(car);
        return rentalMapper.toDtoWihActualReturnDate(rentalRepository.save(rental));
    }

    private Long resolveRequestedUserId(Long requestedUserId) {
        User authenticatedUser = getAuthenticatedUser();
        if (isManager(authenticatedUser)) {
            return requestedUserId;
        }
        if (requestedUserId != null && !requestedUserId.equals(authenticatedUser.getId())) {
            throw new AccessDeniedException("Customers can access only their own rentals");
        }
        return authenticatedUser.getId();
    }

    private Rental getAccessibleRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() ->
                new EntityNotFoundException("Can't find rental wtih id: " + rentalId));
        User authenticatedUser = getAuthenticatedUser();
        if (!isManager(authenticatedUser)
                && !rental.getUserId().equals(authenticatedUser.getId())) {
            throw new AccessDeniedException("Customers can access only their own rentals");
        }
        return rental;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }
        throw new IllegalStateException("Can't find authenticated user");
    }

    private boolean isManager(User user) {
        return user.getRole() == User.Role.MANAGER;
    }
}
