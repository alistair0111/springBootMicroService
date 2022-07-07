package com.microservice.ewallet.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    public Map<String,Object> getProducerConfig(){
        Map<String,Object> producerConfig=new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return producerConfig;
    }

    @Bean
    public ProducerFactory getProducerFactory(){
        return new DefaultKafkaProducerFactory(getProducerConfig());
    }

    @Bean
    public KafkaTemplate<String,String> getKafkaTemplate(){
        return new KafkaTemplate<>(getProducerFactory());
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
