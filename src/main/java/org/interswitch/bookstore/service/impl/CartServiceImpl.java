package org.interswitch.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interswitch.bookstore.dto.request.AddToCartRequest;
import org.interswitch.bookstore.dto.response.AddToCartResponse;
import org.interswitch.bookstore.dto.CartItemDto;
import org.interswitch.bookstore.entities.Book;
import org.interswitch.bookstore.entities.CartItem;
import org.interswitch.bookstore.entities.ShoppingCart;
import org.interswitch.bookstore.exception.GenericException;
import org.interswitch.bookstore.repository.BookRepository;
import org.interswitch.bookstore.repository.CartRepository;
import org.interswitch.bookstore.service.CartService;
import org.interswitch.bookstore.utils.AppendableReferenceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.interswitch.bookstore.utils.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    @Override
    public AddToCartResponse addToCart(AddToCartRequest addToCartRequest) {
        ShoppingCart cart;
        String userId = String.valueOf(AppendableReferenceUtils.getIdFrom(addToCartRequest.getUserReference()));
        Long bookId = AppendableReferenceUtils.getIdFrom(addToCartRequest.getBookReference());
        Book book = getBook(bookId);
        validateBookQuantityLeft(addToCartRequest, book);
        cart = getShoppingCartFor(userId);
        CartItem cartItem = getCartItem(book);
        cartItem.setQuantity(addToCartRequest.getQuantity());
        book.setQuantity(book.getQuantity() - addToCartRequest.getQuantity());
        cart.getItems().add(cartItem);
        cart = cartRepository.save(cart);
        book = bookRepository.save(book);
        return getAddToCartResponse(addToCartRequest, book, cart);
    }
    @Override
    public List<CartItemDto> getCartItems(String cartReference) {
        ShoppingCart cart = cartRepository.findById(AppendableReferenceUtils.getIdFrom(cartReference)).orElse(new ShoppingCart());
        if(Objects.isNull(cart.getItems())){
            return new ArrayList<>();
        }
        return cart.getItems().stream().map(
                CartItemDto::map
        ).collect(Collectors.toList());
    }
    @Override
    public void removeFromCart(String cartReference, String isbn) {
        ShoppingCart cart = getShoppingCart(cartReference);
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getIsbn().equals(isbn)).findFirst().orElse(null);
        if(Objects.nonNull(cartItem)){
            cart.getItems().remove(cartItem);
            cartRepository.save(cart);
        }
    }



    private ShoppingCart getShoppingCart(String cartReference) {
        Long cartId = AppendableReferenceUtils.getIdFrom(cartReference);
        return cartRepository.findById(cartId).orElseThrow(() -> new GenericException(CART_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    private AddToCartResponse getAddToCartResponse(AddToCartRequest addToCartRequest, Book book, ShoppingCart cart) {
        return AddToCartResponse.builder()
                .bookReference(book.getReference())
                .unitPrice(book.getPrice())
                .cartReference(cart.getReference())
                .quantity(addToCartRequest.getQuantity())
                .title(book.getTitle())
                .build();
    }

    private CartItem getCartItem(Book book) {
        return CartItem.builder()
                .price(book.getPrice())
                .title(book.getTitle())
                .bookId(String.valueOf(book.getId()))
                .isbn(book.getIsbn())
                .build();
    }

    private ShoppingCart getShoppingCartFor(String userId) {
        ShoppingCart cart;
        Optional<ShoppingCart> shoppingCart = cartRepository.findByUserId(userId);
        if(shoppingCart.isEmpty()){
            cart = new ShoppingCart();
            cart.setUserId(userId);
            cart.setItems(new ArrayList<>());
        }else {
            cart = shoppingCart.get();
        }
        return cart;
    }

    private Book getBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isEmpty()){
            throw new GenericException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return optionalBook.get();
    }

    private static void validateBookQuantityLeft(AddToCartRequest addToCartRequest, Book book) {
        if(book.getQuantity() <= 0){
            throw new GenericException(OUT_OF_STOCK,HttpStatus.BAD_REQUEST);
        }else if(book.getQuantity() < addToCartRequest.getQuantity()){
            throw new GenericException(String.format("There are only %d of %s left in stock", book.getQuantity(), book.getTitle()),HttpStatus.BAD_REQUEST);
        }
    }
}
