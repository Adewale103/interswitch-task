package org.interswitch.bookstore.service;

import org.interswitch.bookstore.dto.PaymentRequest;
import org.interswitch.bookstore.dto.PaymentResponse;
import org.interswitch.bookstore.dto.PurchaseHistoryDto;

import java.util.List;

public interface PaymentService {
    List<PurchaseHistoryDto> viewPurchaseHistory(String userReference);
    PaymentResponse checkOutViaUssd(PaymentRequest paymentRequest);
    PaymentResponse checkOutViaWeb(PaymentRequest paymentRequest);
    PaymentResponse checkOutViaTransfer(PaymentRequest paymentRequest);
}
