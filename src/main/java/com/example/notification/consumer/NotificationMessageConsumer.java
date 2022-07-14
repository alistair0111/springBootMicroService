package com.example.notification.consumer;

import com.example.notification.service.MailSenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationMessageConsumer {

    private static final String NOTIFICATION_TOPIC="notification";

    @Autowired
    private MailSenderService mailSenderService;

    Logger logger= LoggerFactory.getLogger(NotificationMessageConsumer.class);

    @KafkaListener(topics = NOTIFICATION_TOPIC,groupId = "notificationGrp")
    public void publishNotification(String message) throws JsonProcessingException {

        logger.info("message received -> {}",message);
        mailSenderService.sendNotification(message);

    }
}
