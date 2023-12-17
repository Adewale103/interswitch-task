package org.interswitch.bookstore.dto;
import lombok.*;

import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.interswitch.bookstore.entities.PurchaseHistory;
import org.interswitch.bookstore.enums.PaymentMethod;
import org.interswitch.bookstore.utils.BeanUtilHelper;
import org.springframework.security.access.method.P;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseHistoryDto{
    private String paymentReference;
    private BigDecimal amountPaid;
    private Date paymentDate;
    private PaymentMethod paymentMethod;
    private String accountNumber;
    private String bankName;

    public static PurchaseHistoryDto map(PurchaseHistory purchaseHistory)  {
        return PurchaseHistoryDto.builder()
                .accountNumber(purchaseHistory.getAccountNumber())
                .paymentReference(purchaseHistory.getReference())
                .amountPaid(purchaseHistory.getAmountPaid())
                .paymentDate(purchaseHistory.getPaymentDate())
                .accountNumber(purchaseHistory.getAccountNumber())
                .bankName(purchaseHistory.getBankName())
                .paymentMethod(purchaseHistory.getPaymentMethod())
                .build();
    }
}
