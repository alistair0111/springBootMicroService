package com.example.transaction.dao;
import com.example.transaction.domain.TransactionStatus;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionStatusMessage {

    private String transactionId;
    private TransactionStatus status;
}
