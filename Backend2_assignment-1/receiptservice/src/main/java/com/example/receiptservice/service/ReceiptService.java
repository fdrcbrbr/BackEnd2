package com.example.receiptservice.service;

import com.example.receiptservice.dto.ReceiptRequest;
import com.example.receiptservice.model.Receipt;
import com.example.receiptservice.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record ReceiptService(ReceiptRepository ReceiptRepository) {

    public void send(ReceiptRequest ReceiptRequest){
        ReceiptRepository.save(
                Receipt.builder()
                        .toCustomerId(ReceiptRequest.toCustomerId())
                        .toItemId(ReceiptRequest.toItemId())
                        .toCustomerUserName(ReceiptRequest.toCustomerName())
                        .toItemName(ReceiptRequest.toItemName())
                        .sender("The best web-shop")
                        .message(ReceiptRequest.welcomeMessage())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
