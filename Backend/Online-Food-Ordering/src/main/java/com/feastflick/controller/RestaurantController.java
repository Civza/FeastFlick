package com.feastflick.controller;

import com.feastflick.dto.RestaurantDto;
import com.feastflick.model.Restaurant;
import com.feastflick.model.User;
import com.feastflick.service.IUserService;
import com.feastflick.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private IUserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String token, @RequestParam String keyword) throws Exception {
        User user = userService.findUserByToken(token);

        List<Restaurant> restaurant = restaurantService.searchRestaurants(keyword);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);

        List<Restaurant> restaurant = restaurantService.getAllRestaurants();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        User user = userService.findUserByToken(token);

        Restaurant restaurant = restaurantService.getRestaurant(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorite")
    public ResponseEntity<RestaurantDto> addToFavorites(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        User user = userService.findUserByToken(token);

        RestaurantDto restaurant = restaurantService.addToFavourites(id, user);


        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
