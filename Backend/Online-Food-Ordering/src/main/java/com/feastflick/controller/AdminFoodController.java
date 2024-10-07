package com.feastflick.controller;

import com.feastflick.model.Food;
import com.feastflick.model.Restaurant;
import com.feastflick.model.User;
import com.feastflick.request.CreateFoodRequest;
import com.feastflick.response.MessageResponse;
import com.feastflick.service.IFoodService;
import com.feastflick.service.IRestaurantService;
import com.feastflick.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private IFoodService foodService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        Restaurant restaurant = restaurantService.getRestaurant(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        User user = userService.findUserByToken(token);
        foodService.deleteFood(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Successfully deleted food");

        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        User user = userService.findUserByToken(token);
        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }


}
