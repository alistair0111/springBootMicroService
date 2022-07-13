package com.example.Transaction.service.request;
import com.example.Transaction.domain.Transaction;
import com.example.Transaction.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletUpdateRequest {

    private Integer userId;
    private Double amount;
    private boolean withdraw;
    private String description;


    public Transaction toTransaction(){
        return  Transaction.builder().amount(amount)
                .senderId(withdraw?-1:userId)
                .receiverId(withdraw?userId:-1)
                .status(TransactionStatus.PENDING)
                .description(description)
                .transactionId(UUID.randomUUID().toString()).build();
    }
}
