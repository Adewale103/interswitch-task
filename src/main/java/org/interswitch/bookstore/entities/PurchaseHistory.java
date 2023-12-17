package org.interswitch.bookstore.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.interswitch.bookstore.enums.PaymentMethod;
import org.interswitch.bookstore.enums.Role;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@Table(name = "purchase_histories")
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseHistory extends AppendableReference{
    private BigDecimal amountPaid;
    private String cartId;
    private String userId;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String accountNumber;
    private String bankName;

}
