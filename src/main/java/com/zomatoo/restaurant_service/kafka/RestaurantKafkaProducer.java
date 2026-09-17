package com.zomatoo.restaurant_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RestaurantKafkaProducer {
    @Autowired
    private KafkaTemplate<String , String> kafkaTemplate;

             public void sendMessage(String msg ){
                  kafkaTemplate.send("add-restaurant", msg);
       }
}
