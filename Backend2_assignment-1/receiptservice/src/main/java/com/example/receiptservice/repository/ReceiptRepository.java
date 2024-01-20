package com.example.receiptservice.repository;

import com.example.receiptservice.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository
        extends JpaRepository<Receipt, Long> {
}
