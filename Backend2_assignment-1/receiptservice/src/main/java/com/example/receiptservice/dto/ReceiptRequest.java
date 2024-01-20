package com.example.receiptservice.dto;

public record ReceiptRequest(Long toCustomerId,
                             String toCustomerName,
                             String welcomeMessage,
                             Long toItemId,
                             String toItemName) {
}
