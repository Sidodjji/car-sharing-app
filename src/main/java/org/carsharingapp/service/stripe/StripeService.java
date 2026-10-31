package org.carsharingapp.service.stripe;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    private final StripeClient stripeClient;
    private final String successUrl;
    private final String cancelUrl;

    public StripeService(
            StripeClient stripeClient,
            @Value("${stripe.success-url}") String successUrl,
            @Value("${stripe.cancel-url}") String cancelUrl
    ) {
        this.stripeClient = stripeClient;
        this.successUrl = successUrl;
        this.cancelUrl = cancelUrl;
    }

    public Session createCheckoutSession(
            Long rentalId,
            long amountTotal,
            String currency
    ) throws StripeException {
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(currency)
                        .setUnitAmount(amountTotal)
                        .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData
                                        .builder()
                                        .setName("Car rental #" + rentalId)
                                        .build()
                        )
                        .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .setClientReferenceId(rentalId.toString())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(priceData)
                                .setQuantity(1L)
                                .build()
                )
                .build();

        return stripeClient.v1().checkout().sessions().create(params);
    }
}
