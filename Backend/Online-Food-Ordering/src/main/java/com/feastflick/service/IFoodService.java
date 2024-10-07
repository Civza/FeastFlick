package com.feastflick.service;

import com.feastflick.model.Category;
import com.feastflick.model.Food;
import com.feastflick.model.Restaurant;
import com.feastflick.request.CreateFoodRequest;

import java.util.List;

public interface IFoodService {
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);
    public void deleteFood(Long foodId) throws Exception;
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) throws Exception;
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;
    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
