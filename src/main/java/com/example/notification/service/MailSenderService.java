package com.example.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {


    @Autowired
    ObjectMapper mapper;

    @Autowired
    JavaMailSenderImpl javaMailSender;



    public void sendNotification(String message) throws JsonProcessingException {


        Logger logger= LoggerFactory.getLogger(MailSenderService.class);

        JSONObject jsonObject=mapper.readValue(message,JSONObject.class);
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        if(jsonObject.containsKey("to"))
            mailMessage.setTo((String) jsonObject.get("to"));
        if(jsonObject.containsKey("cc"))
            mailMessage.setCc((String) jsonObject.get("cc"));
        if(jsonObject.containsKey("subject"))
            mailMessage.setSubject((String) jsonObject.get("subject"));
        if(jsonObject.containsKey("body"))
            mailMessage.setText((String) jsonObject.get("body"));

        logger.info(mapper.writeValueAsString(mailMessage));

    }
}
