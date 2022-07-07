package com.microservice.ewallet.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.ewallet.user.dao.UserMessage;
import com.microservice.ewallet.user.domain.MyUser;
import com.microservice.ewallet.user.repository.MyUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final static String USER_CREATE_TOPIC="USER_CREATE";

    private Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ObjectMapper mapper;

    public MyUser saveUser(MyUser myUser)  {
        MyUser user =  myUserRepository.save(myUser);
        UserMessage message=new UserMessage(String.valueOf(user.getId()));
        try {
            kafkaTemplate.send(USER_CREATE_TOPIC, mapper.writeValueAsString(message));
        }catch (Exception e){
            logger.error("Exception in serialization message");
        }
        return user;
    }

    public MyUser getUser(Integer userId) {
        return myUserRepository.findById(userId).orElseThrow();
    }
}
