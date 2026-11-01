package org.carsharingapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.stripe.model.checkout.Session;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.carsharingapp.dto.payment.CreatePaymentRequestDto;
import org.carsharingapp.mapper.PaymentMapper;
import org.carsharingapp.model.*;
import org.carsharingapp.repository.*;
import org.carsharingapp.service.payment.PaymentServiceImpl;
import org.carsharingapp.service.stripe.StripeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;

class PaymentServiceTest {
    private final PaymentRepository payments = mock(PaymentRepository.class);
    private final RentalRepository rentals = mock(RentalRepository.class);
    private final CarRepository cars = mock(CarRepository.class);
    private final PaymentMapper mapper = mock(PaymentMapper.class);
    private final StripeService stripe = mock(StripeService.class);
    private final PaymentServiceImpl service = new PaymentServiceImpl(payments, mapper, rentals, cars, stripe);
    private Rental rental;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setRole(User.Role.CUSTOMER);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
        rental = new Rental();
        rental.setId(2L);
        rental.setUserId(1L);
        rental.setCarId(3L);
        rental.setRentalDate(LocalDate.of(2026, 1, 1));
        rental.setReturnDate(LocalDate.of(2026, 1, 3));
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @ParameterizedTest
    @CsvSource({"0,unpaid,UNPAID,1250", "2,paid,PAID,2500", "1,no_payment_required,NO_PAYMENT_REQUIRED,1250"})
    void createsPayment(int days, String status, Payment.PaymentStatus expected, long amount) throws Exception {
        rental.setReturnDate(rental.getRentalDate().plusDays(days));
        when(rentals.findById(2L)).thenReturn(Optional.of(rental));
        Car car = new Car();
        car.setDailyFee(new BigDecimal("12.50"));
        when(cars.findById(3L)).thenReturn(Optional.of(car));
        Session session = new Session();
        session.setId("cs_test");
        session.setUrl("https://example.com/pay");
        session.setPaymentStatus(status);
        when(stripe.createCheckoutSession(2L, amount, "usd")).thenReturn(session);
        when(payments.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        service.createPayment(new CreatePaymentRequestDto(2L));
        verify(payments).save(argThat(payment -> payment.getRental() == rental
                && payment.getAmountTotal() == amount && payment.getPaymentStatus() == expected
                && "cs_test".equals(payment.getSessionId())));
    }

    @Test
    void rejectsForeignRental() {
        rental.setUserId(9L);
        when(rentals.findById(2L)).thenReturn(Optional.of(rental));
        assertThrows(AccessDeniedException.class, () -> service.createPayment(new CreatePaymentRequestDto(2L)));
        verifyNoInteractions(stripe);
    }

    @Test
    void rejectsDuplicatePayment() {
        when(rentals.findById(2L)).thenReturn(Optional.of(rental));
        when(payments.existsByRental_Id(2L)).thenReturn(true);
        assertThrows(org.carsharingapp.exeption.PaymentExeption.class,
                () -> service.createPayment(new CreatePaymentRequestDto(2L)));
        verifyNoInteractions(stripe);
    }

    @Test
    void readsOwnPaymentAndRejectsForeignPayment() {
        Payment payment = new Payment();
        payment.setRental(rental);
        when(payments.findById(4L)).thenReturn(Optional.of(payment));
        service.getPaymentById(4L);
        verify(mapper).toDto(payment);
        rental.setUserId(9L);
        assertThrows(AccessDeniedException.class, () -> service.getPaymentById(4L));
    }

    @Test
    void rejectsMissingPaymentAndAnonymousUser() {
        assertThrows(org.carsharingapp.exeption.EntityNotFoundException.class, () -> service.getPaymentById(4L));
        SecurityContextHolder.clearContext();
        assertThrows(IllegalStateException.class, () -> service.createPayment(new CreatePaymentRequestDto(2L)));
    }
}
