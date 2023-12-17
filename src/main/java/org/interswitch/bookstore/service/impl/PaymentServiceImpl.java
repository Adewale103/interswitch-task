package org.interswitch.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.interswitch.bookstore.dto.request.PaymentRequest;
import org.interswitch.bookstore.dto.response.PaymentResponse;
import org.interswitch.bookstore.dto.PurchaseHistoryDto;
import org.interswitch.bookstore.entities.PurchaseHistory;
import org.interswitch.bookstore.entities.ShoppingCart;
import org.interswitch.bookstore.enums.PaymentMethod;
import org.interswitch.bookstore.exception.GenericException;
import org.interswitch.bookstore.repository.CartRepository;
import org.interswitch.bookstore.repository.PurchaseHistoryRepository;
import org.interswitch.bookstore.service.PaymentService;
import org.interswitch.bookstore.utils.AppendableReferenceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.interswitch.bookstore.utils.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CartRepository cartRepository;

    @Override
    public List<PurchaseHistoryDto> viewPurchaseHistory(String userReference) {
        String userId = String.valueOf(AppendableReferenceUtils.getIdFrom(userReference));
        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findByUserId(userId).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
        return purchaseHistories.stream().map(
                PurchaseHistoryDto::map
        ).collect(Collectors.toList());
    }

    @Override
    public PaymentResponse checkOutViaUssd(PaymentRequest paymentRequest) {
        ShoppingCart cart = getShoppingCart(paymentRequest.getCartReference());
        BigDecimal total = cart.getItems().stream().map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(total.compareTo(BigDecimal.ZERO) > 0) {
            validatePaymentDetails(paymentRequest, total);
            PurchaseHistory purchaseHistory = getPurchaseHistory(paymentRequest, total, cart, PaymentMethod.USSD);
            purchaseHistory = purchaseHistoryRepository.save(purchaseHistory);
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
            return PaymentResponse.map(paymentRequest, total, purchaseHistory.getReference());
        }
        throw new GenericException(CART_IS_EMPTY, HttpStatus.BAD_REQUEST);
    }
    @Override
    public PaymentResponse checkOutViaWeb(PaymentRequest paymentRequest) {
        ShoppingCart cart = getShoppingCart(paymentRequest.getCartReference());
        BigDecimal total = cart.getItems().stream().map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(total.compareTo(BigDecimal.ZERO) > 0) {
            validatePaymentDetails(paymentRequest, total);
            PurchaseHistory purchaseHistory = getPurchaseHistory(paymentRequest, total, cart, PaymentMethod.ONLINE_BANKING);
            purchaseHistory = purchaseHistoryRepository.save(purchaseHistory);
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
            return PaymentResponse.map(paymentRequest, total, purchaseHistory.getReference());
        }
        throw new GenericException(CART_IS_EMPTY, HttpStatus.BAD_REQUEST);
    }

    @Override
    public PaymentResponse checkOutViaTransfer(PaymentRequest paymentRequest) {
        ShoppingCart cart = getShoppingCart(paymentRequest.getCartReference());
        BigDecimal total = cart.getItems().stream().map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(total.compareTo(BigDecimal.ZERO) > 0) {
            validatePaymentDetails(paymentRequest, total);
            PurchaseHistory purchaseHistory = getPurchaseHistory(paymentRequest, total, cart, PaymentMethod.TRANSFER);
            purchaseHistory = purchaseHistoryRepository.save(purchaseHistory);
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
            return PaymentResponse.map(paymentRequest, total, purchaseHistory.getReference());
        }
        throw new GenericException(CART_IS_EMPTY, HttpStatus.BAD_REQUEST);
    }

    private ShoppingCart getShoppingCart(String cartReference) {
        Long cartId = AppendableReferenceUtils.getIdFrom(cartReference);
        return cartRepository.findById(cartId).orElseThrow(() -> new GenericException(CART_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    private PurchaseHistory getPurchaseHistory(PaymentRequest paymentRequest, BigDecimal total, ShoppingCart cart, PaymentMethod paymentMethod) {
        return PurchaseHistory.builder()
                .paymentMethod(paymentMethod)
                .accountNumber(paymentRequest.getAccountNumber())
                .amountPaid(total)
                .bankName(paymentRequest.getBankName())
                .cartId(String.valueOf(cart.getId()))
                .userId(String.valueOf(AppendableReferenceUtils.getIdFrom(paymentRequest.getUserReference())))
                .build();
    }

    private void validatePaymentDetails(PaymentRequest paymentRequest, BigDecimal total) {
        if(!paymentRequest.getPaymentMethod().matches("^(USSD|TRANSFER|ONLINE_BANKING)$")){
            throw new GenericException(INVALID_PAYMENT, HttpStatus.BAD_REQUEST);
        }
        if(paymentRequest.getAccountBalance().compareTo(total) < 0){
            throw new GenericException(INSUFFICIENT_BALANCE,HttpStatus.BAD_REQUEST);
        }
        if(paymentRequest.getAccountNumber().length() != 10){
            throw new GenericException(INVALID_ACCOUNT_NUMBER,HttpStatus.BAD_REQUEST);
        }
        if(paymentRequest.getTransactionPin().length() != 4){
            throw new GenericException(INVALID_TRANSACTION_PIN,HttpStatus.BAD_REQUEST);
        }
    }
}
