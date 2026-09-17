package com.zomatoo.restaurant_service.controller;

import com.zomatoo.restaurant_service.entity.Restaurant;
import com.zomatoo.restaurant_service.service.RestaurantService;
import com.zomatoo.restaurant_service.service.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

@Autowired
    private RestaurantService restaurantService;
@PostMapping("/save")
           public Restaurant saveRestaurant(@RequestBody Restaurant Xyz ){
          return   restaurantService.saveRestaurant(Xyz);


}



}