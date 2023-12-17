package org.interswitch.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.interswitch.bookstore.dto.*;
import org.interswitch.bookstore.dto.request.AddToCartRequest;
import org.interswitch.bookstore.dto.response.AddToCartResponse;
import org.interswitch.bookstore.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<ResponseDto> addToCart(@RequestBody AddToCartRequest addToCartRequest) throws IOException {
        AddToCartResponse addToCartResponse = cartService.addToCart(addToCartRequest);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(addToCartResponse,"successful"), HttpStatus.CREATED);
    }

    @GetMapping(value = "/get/{reference}", produces = "application/json")
    public ResponseEntity<ResponseDto> getCartItems(@PathVariable() String reference) {
        List<CartItemDto> items = cartService.getCartItems(reference);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(items,"successful"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{cartReference}/{isbn}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromCart(@PathVariable String cartReference, @PathVariable String isbn) {
        cartService.removeFromCart(cartReference,isbn);
    }

}
