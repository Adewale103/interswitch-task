package org.interswitch.bookstore.service;

import org.interswitch.bookstore.dto.request.AddToCartRequest;
import org.interswitch.bookstore.dto.response.AddToCartResponse;
import org.interswitch.bookstore.dto.CartItemDto;

import java.util.List;

public interface CartService {
    AddToCartResponse addToCart(AddToCartRequest addToCartRequest);
    List<CartItemDto> getCartItems(String cartReference);
    void removeFromCart(String cartReference,String isbn);
}
