package org.interswitch.bookstore.dto;


import lombok.*;
import org.interswitch.bookstore.entities.CartItem;


import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private String title;
    private String isbn;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private int quantity;
    private Date addedAt;

    public static CartItemDto map(CartItem cartItem){
        return CartItemDto.builder()
                .title(cartItem.getTitle())
                .isbn(cartItem.getIsbn())
                .unitPrice(cartItem.getPrice())
                .totalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .quantity(cartItem.getQuantity())
                .addedAt(cartItem.getAddedAt())
                .build();
    }
}
