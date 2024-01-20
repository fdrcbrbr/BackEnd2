package com.example.api.dto;

public record ReceiptRequest(Long toCustomerId,
                             String toCustomerName,
                             String welcomeMessage,
                             Long toItemId,
                             String toItemName) {
}
