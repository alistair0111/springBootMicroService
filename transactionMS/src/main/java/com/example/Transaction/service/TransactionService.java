package com.example.Transaction.service;

import com.example.Transaction.dao.TransactionMessage;
import com.example.Transaction.dao.TransactionStatusMessage;
import com.example.Transaction.dao.UserResponse;
import com.example.Transaction.domain.Transaction;
import com.example.Transaction.domain.TransactionStatus;
import com.example.Transaction.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

    Logger logger= LoggerFactory.getLogger(TransactionService.class);

    private static final String NOTIFICATION_TOPIC="notification";

    @Autowired
    TransactionRepository transactionRepository;


    @Autowired
    private KafkaTemplate kafkaTemplate;

    private final String walletUpdateTopic = "WALLET_TRANS";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;


    public void postWalletUpdateMessage(Transaction transaction){
        try {
            //transaction will be stored in status : PENDING
            transactionRepository.save(transaction);
            TransactionMessage message=new TransactionMessage(transaction.getTransactionId(),transaction.getSenderId(),transaction.getReceiverId(),transaction.getAmount(),Boolean.TRUE);
            kafkaTemplate.send(walletUpdateTopic, mapper.writeValueAsString(message));
        }catch (Exception e){
            logger.error("Exception in serialization message");
        }
    }

    public void updateTransaction(TransactionStatusMessage transactionStatusMessage) throws JsonProcessingException {

        /**
         * 1. post an update query in the repository for the DB
         * 2. Get the transaction from DB and update the status and save it back
         * */

        Transaction transaction=transactionRepository.findByTransactionId(transactionStatusMessage.getTransactionId());
        transaction.setStatus(transactionStatusMessage.getStatus());
        transactionRepository.save(transaction);
        UserResponse sender= restTemplate.getForObject("http://localhost:8001/user/profile?userId="+transaction.getSenderId(), UserResponse.class);


        if(transactionStatusMessage.getStatus().equals(TransactionStatus.FAILED)){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("to",sender.getEmail());
            jsonObject.put("subject","Transaction update");
            jsonObject.put("body","Hi "+sender.getUsername()+"! \n Transaction with ID:"+transaction.getTransactionId()+" has been failed");
            kafkaTemplate.send(NOTIFICATION_TOPIC,mapper.writeValueAsString(jsonObject));
        }else{
            UserResponse receiver= restTemplate.getForObject("http://localhost:8001/user/profile?userId="+transaction.getReceiverId(), UserResponse.class);
            JSONObject senderJsonObject=new JSONObject();
            senderJsonObject.put("to",sender.getEmail());
            senderJsonObject.put("subject","Transaction update");
            senderJsonObject.put("body","Hi "+sender.getUsername()+"! \n Transaction with ID:"+transaction.getTransactionId()+" has been processed");
            kafkaTemplate.send(NOTIFICATION_TOPIC,mapper.writeValueAsString(senderJsonObject));
            JSONObject receiverJsonObject=new JSONObject();
            receiverJsonObject.put("to",receiver.getEmail());
            receiverJsonObject.put("subject","Transaction update");
            receiverJsonObject.put("body","Hi "+receiver.getUsername()+"! \n Amount of Rs." + transaction.getAmount() +" has been credited to your ewallet");
            kafkaTemplate.send(NOTIFICATION_TOPIC,mapper.writeValueAsString(receiverJsonObject));
        }
    }
}
