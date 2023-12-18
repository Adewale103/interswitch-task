package org.interswitch.bookstore.service;


import lombok.extern.slf4j.Slf4j;
import org.interswitch.bookstore.dto.CartItemDto;
import org.interswitch.bookstore.dto.request.AddToCartRequest;
import org.interswitch.bookstore.dto.response.AddToCartResponse;
import org.interswitch.bookstore.entities.Book;
import org.interswitch.bookstore.entities.CartItem;
import org.interswitch.bookstore.entities.ShoppingCart;
import org.interswitch.bookstore.repository.BookRepository;
import org.interswitch.bookstore.repository.CartRepository;
import org.interswitch.bookstore.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class CartServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartService cartService;

    AddToCartRequest addToCartRequest;
    Book book;
    ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        cartService  = new CartServiceImpl(bookRepository, cartRepository);
        addToCartRequest = new AddToCartRequest();
        addToCartRequest.setQuantity(3);
        addToCartRequest.setBookReference("1_iuytrj");
        addToCartRequest.setUserReference("1_ertyuh");
        book = new Book();
        book.setQuantity(4);
        book.setAuthors(new ArrayList<>());
        book.setDescription("I love it");
        book.setIsbn("23423478");
        book.setPrice(BigDecimal.valueOf(34780));
        book.setTitle("Get Off");
        book.setReference("1_iuytrj");
        shoppingCart = new ShoppingCart();
        shoppingCart.setItems(new ArrayList<>());
        shoppingCart.setUserId(String.valueOf(1));
    }

    @Test
    public void testThatCartCanBeAdded(){
        when(cartRepository.findByUserId(anyString())).thenReturn(Optional.of(shoppingCart));
        when(cartRepository.save(shoppingCart)).thenReturn(shoppingCart);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        AddToCartResponse response = cartService.addToCart(addToCartRequest);
        assertThat(response.getTitle()).isEqualTo("Get Off");

    }

    @Test
    void getAllItemsTest() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCart));
        List<CartItemDto> cartItemDtoList = cartService.getCartItems("1_iuytrj");
        assertThat(cartItemDtoList).isNotEqualTo(null);
    }

    @Test
    void removeFromCartTest() {
        CartItem cartItem = new CartItem();
        cartItem.setIsbn(book.getIsbn());
        cartItem.setQuantity(1);
        cartItem.setPrice(BigDecimal.valueOf(1900));
        shoppingCart.getItems().add(cartItem);
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCart));
        assertEquals(shoppingCart.getItems().size(),1);
        cartService.removeFromCart(shoppingCart.getReference(), book.getIsbn());
        assertEquals(shoppingCart.getItems().size(),0);
    }

}