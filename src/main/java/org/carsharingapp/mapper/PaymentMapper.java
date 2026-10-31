package org.carsharingapp.mapper;

import org.carsharingapp.config.MapperConfig;
import org.carsharingapp.dto.payment.CreatePaymentRequestDto;
import org.carsharingapp.dto.payment.PaymentDto;
import org.carsharingapp.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "rental", ignore = true)
    Payment toModel(CreatePaymentRequestDto requestDto);

    @Mapping(target = "rentalId", source = "rental.id")
    PaymentDto toDto(Payment payment);
}
