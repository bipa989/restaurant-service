package com.zomatoo.restaurant_service.service;

import com.zomatoo.restaurant_service.entity.Restaurant;
import com.zomatoo.restaurant_service.kafka.RestaurantKafkaProducer;
import com.zomatoo.restaurant_service.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

@Autowired
private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantKafkaProducer restaurantKafkaProducer;


    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
     Restaurant rts = restaurantRepository.save(restaurant);

        restaurantKafkaProducer.sendMessage("Restaurant added: "+rts.getName());

     return rts;
    }

    @Override
    public Restaurant getAllRestaurant(Restaurant restaurant) {
        return null;
    }
}

