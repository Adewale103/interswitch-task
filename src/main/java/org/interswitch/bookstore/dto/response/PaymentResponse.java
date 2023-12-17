package org.interswitch.bookstore.dto.response;

import lombok.*;
import org.interswitch.bookstore.dto.request.PaymentRequest;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String cartReference;
    private String accountNumber;
    private BigDecimal bankBalance;
    private String bankName;
    private String paymentReference;
    private BigDecimal totalPrice;
    private String phoneNumber; // For USSD

    public static PaymentResponse map(PaymentRequest paymentRequest, BigDecimal total, String paymentReference){
        return PaymentResponse.builder()
                .cartReference(paymentRequest.getCartReference())
                .accountNumber(paymentRequest.getAccountNumber())
                .bankName(paymentRequest.getBankName())
                .phoneNumber(paymentRequest.getPhoneNumber())
                .bankBalance(paymentRequest.getAccountBalance().subtract(total))
                .totalPrice(total)
                .paymentReference(paymentReference)
                .build();
    }
}
