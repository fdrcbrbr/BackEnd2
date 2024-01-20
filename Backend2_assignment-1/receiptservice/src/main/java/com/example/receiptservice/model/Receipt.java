package com.example.receiptservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Receipt {

    @Id
    @SequenceGenerator(
            name = "receipt_id_sequence",
            sequenceName = "receipt_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "receipt_id_sequence"
    )
    private Long receiptId;
    private Long toCustomerId;
    private Long toItemId;
    private String toCustomerUserName;
    private String toItemName;
    private String sender;
    private String message;
    private LocalDateTime sentAt;
}
