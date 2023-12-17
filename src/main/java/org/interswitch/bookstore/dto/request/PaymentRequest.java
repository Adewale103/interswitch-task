package org.interswitch.bookstore.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.interswitch.bookstore.annotation.ValidPaymentMethod;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    private String transactionPin;
    @ValidPaymentMethod
    private String paymentMethod;
    private String cartReference;
    private String accountNumber;
    private String bankName;
    private String userReference;
    private BigDecimal accountBalance;
    private String phoneNumber; // For USSD
}
