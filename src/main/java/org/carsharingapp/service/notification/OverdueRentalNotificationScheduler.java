package org.carsharingapp.service.notification;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.carsharingapp.model.Rental;
import org.carsharingapp.repository.RentalRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OverdueRentalNotificationScheduler {
    private final RentalRepository rentalRepository;
    private final NotificationService notificationService;

    @Scheduled(
            cron = "${rental.overdue.check.cron:0 0 9 * * *}",
            zone = "${rental.overdue.check.zone:Europe/Kiev}"
    )
    public void checkOverdueRentals() {
        LocalDate overdueThresholdDate = LocalDate.now().plusDays(1);
        List<Rental> overdueRentals = rentalRepository
                .findByActiveTrueAndReturnDateLessThanEqual(overdueThresholdDate);
        if (overdueRentals.isEmpty()) {
            notificationService.sendMessage("No rentals overdue today!");
            return;
        }
        overdueRentals.forEach(rental ->
                notificationService.sendMessage(buildOverdueRentalMessage(rental)));
    }

    private String buildOverdueRentalMessage(Rental rental) {
        return """
                <b>Overdue rental found</b>
                Rental id: <code>%d</code>
                User id: <code>%d</code>
                Car id: <code>%d</code>
                Rental date: %s
                Return date: %s
                Actual return date: %s
                """.formatted(
                rental.getId(),
                rental.getUserId(),
                rental.getCarId(),
                rental.getRentalDate(),
                rental.getReturnDate(),
                rental.getActualReturnDate()
        );
    }
}
