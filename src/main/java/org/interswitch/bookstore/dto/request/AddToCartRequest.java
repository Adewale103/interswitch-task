package org.interswitch.bookstore.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddToCartRequest {
    private String userReference;
    private String bookReference;
    private int quantity;
}
