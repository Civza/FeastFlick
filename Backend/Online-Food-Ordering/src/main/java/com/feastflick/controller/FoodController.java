package com.feastflick.controller;

import com.feastflick.model.Food;
import com.feastflick.model.Restaurant;
import com.feastflick.model.User;
import com.feastflick.request.CreateFoodRequest;
import com.feastflick.service.IFoodService;
import com.feastflick.service.IRestaurantService;
import com.feastflick.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private IFoodService foodService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        List<Food> foods = foodService.searchFood(keyword);

        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam(required = false) String food_category, @RequestParam boolean vegetarian, @RequestParam boolean seasonal, @RequestParam boolean nonveg, @PathVariable Long restaurantId, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);
        List<Food> foods = foodService.getRestaurantFood(restaurantId, vegetarian, nonveg, seasonal, food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
