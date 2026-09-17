package com.zomatoo.restaurant_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RestaurantKafkaConsumer {

    @KafkaListener(
            topics = "add-restaurant",
            groupId = "restaurant-group-test"
    )
    public void consumeMessage(String message) {

        System.out.println("================================");
        System.out.println("Received message: " + message);
        System.out.println("================================");
    }
}