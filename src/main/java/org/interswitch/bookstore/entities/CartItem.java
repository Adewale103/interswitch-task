package org.interswitch.bookstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.interswitch.bookstore.enums.Genre;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@Table(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends AppendableReference{
    @Column(nullable = false, length = 50)
    private String title;

    private String isbn;

    private String bookId;
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date addedAt;

}
