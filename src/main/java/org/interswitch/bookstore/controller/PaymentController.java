package org.interswitch.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.interswitch.bookstore.dto.*;
import org.interswitch.bookstore.dto.request.PaymentRequest;
import org.interswitch.bookstore.dto.response.PaymentResponse;
import org.interswitch.bookstore.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping(value = "/ussd", produces = "application/json")
    public ResponseEntity<ResponseDto> checkOutViaUssd(@RequestBody PaymentRequest paymentRequest){
      PaymentResponse paymentResponse = paymentService.checkOutViaUssd(paymentRequest);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(paymentResponse,"successful"), HttpStatus.OK);
    }


    @PostMapping(value = "/web", produces = "application/json")
    public ResponseEntity<ResponseDto> checkOutViaWeb(@RequestBody PaymentRequest paymentRequest){
        PaymentResponse paymentResponse = paymentService.checkOutViaWeb(paymentRequest);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(paymentResponse,"successful"), HttpStatus.OK);
    }


    @PostMapping(value = "/transfer", produces = "application/json")
    public ResponseEntity<ResponseDto> checkOutViaTransfer(@RequestBody PaymentRequest paymentRequest){
        PaymentResponse paymentResponse = paymentService.checkOutViaTransfer(paymentRequest);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(paymentResponse,"successful"), HttpStatus.CREATED);
    }


    @GetMapping(value = "/view/{reference}", produces = "application/json")
    public ResponseEntity<ResponseDto> viewPurchaseHistory(@PathVariable() String reference) {
        List<PurchaseHistoryDto> items = paymentService.viewPurchaseHistory(reference);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(items,"successful"), HttpStatus.OK);
    }
}
