package org.interswitch.bookstore.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.interswitch.bookstore.dto.AuthorDto;
import org.interswitch.bookstore.dto.BookDto;
import org.interswitch.bookstore.dto.PurchaseHistoryDto;
import org.interswitch.bookstore.dto.request.AuthorRequest;
import org.interswitch.bookstore.dto.request.BookRequest;
import org.interswitch.bookstore.dto.request.PaymentRequest;
import org.interswitch.bookstore.dto.response.AmazonResponse;
import org.interswitch.bookstore.dto.response.PagedResponse;
import org.interswitch.bookstore.dto.response.PaymentResponse;
import org.interswitch.bookstore.entities.*;
import org.interswitch.bookstore.enums.Genre;
import org.interswitch.bookstore.enums.PaymentMethod;
import org.interswitch.bookstore.repository.BookRepository;
import org.interswitch.bookstore.repository.CartRepository;
import org.interswitch.bookstore.repository.PurchaseHistoryRepository;
import org.interswitch.bookstore.service.impl.BookServiceImpl;
import org.interswitch.bookstore.service.impl.PaymentServiceImpl;
import org.interswitch.bookstore.service.storage.StorageService;
import org.interswitch.bookstore.service.storage.UploadObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class PaymentServiceTest {
    @Mock
    private PurchaseHistoryRepository purchaseHistoryRepository;
    @Mock
    private CartRepository cartRepository;
    private PaymentService paymentService;
    private PaymentRequest paymentRequest;
    private ShoppingCart shoppingCart;
    private CartItem cartItem;
    private PurchaseHistory purchaseHistory;



    @BeforeEach
    void setUp() {
        cartItem = new CartItem();
        cartItem.setIsbn("347890987");
        cartItem.setQuantity(1);
        cartItem.setPrice(BigDecimal.valueOf(1900));
        shoppingCart = new ShoppingCart();
        shoppingCart.setItems(List.of(cartItem));
        shoppingCart.setUserId(String.valueOf(1));
        paymentService  = new PaymentServiceImpl(purchaseHistoryRepository, cartRepository);
        paymentRequest = new PaymentRequest();
        paymentRequest.setTransactionPin("1234");
        paymentRequest.setPaymentMethod("USSD");
        paymentRequest.setCartReference("1_ICQ4AEE");
        paymentRequest.setAccountNumber("0130008221");
        paymentRequest.setBankName("Gt Bank");
        paymentRequest.setUserReference("1_87zUUNnbR");
        paymentRequest.setAccountBalance(BigDecimal.valueOf(100000));
        paymentRequest.setPhoneNumber("+2349021803344");
        purchaseHistory = new PurchaseHistory();
        purchaseHistory.setPaymentMethod(PaymentMethod.valueOf("USSD"));
        purchaseHistory.setCartId("1");
        purchaseHistory.setAccountNumber("0130008221");
        purchaseHistory.setBankName("Gt Bank");
        purchaseHistory.setUserId("1");
        purchaseHistory.setAmountPaid(BigDecimal.valueOf(1000));
        purchaseHistory.setReference("1_ICQ4AEE");
    }

    @Test
    public void viewPurchaseHistoryTest(){
      when(purchaseHistoryRepository.findByUserId(anyString())).thenReturn(Optional.of(List.of(purchaseHistory)));
      List<PurchaseHistoryDto> response = paymentService.viewPurchaseHistory("1_hLrFyzQ");
      assertThat(response).isNotEqualTo(null);
    }

    @Test
    void checkOutViaUssdTest() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCart));
        when(cartRepository.save(shoppingCart)).thenReturn(shoppingCart);
        when(purchaseHistoryRepository.save(any(PurchaseHistory.class))).thenReturn(purchaseHistory);
        PaymentResponse paymentResponse = paymentService.checkOutViaUssd(paymentRequest);
        assertThat(paymentResponse).isNotEqualTo(null);
    }

    @Test
    void checkOutViaWebTest() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCart));
        when(cartRepository.save(shoppingCart)).thenReturn(shoppingCart);
        when(purchaseHistoryRepository.save(any(PurchaseHistory.class))).thenReturn(purchaseHistory);
        PaymentResponse paymentResponse = paymentService.checkOutViaWeb(paymentRequest);
        assertThat(paymentResponse).isNotEqualTo(null);
    }

    @Test
    void checkOutViaTransferTest() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCart));
        when(cartRepository.save(shoppingCart)).thenReturn(shoppingCart);
        when(purchaseHistoryRepository.save(any(PurchaseHistory.class))).thenReturn(purchaseHistory);
        PaymentResponse paymentResponse = paymentService.checkOutViaTransfer(paymentRequest);
        assertThat(paymentResponse).isNotEqualTo(null);
    }


}
