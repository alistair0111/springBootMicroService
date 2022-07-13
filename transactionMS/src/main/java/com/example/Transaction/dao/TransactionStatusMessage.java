package com.example.Transaction.dao;
import com.example.Transaction.domain.TransactionStatus;
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
