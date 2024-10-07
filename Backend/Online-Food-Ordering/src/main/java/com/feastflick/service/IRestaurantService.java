package com.feastflick.service;

import com.feastflick.dto.RestaurantDto;
import com.feastflick.model.Restaurant;
import com.feastflick.model.User;
import com.feastflick.request.CreateRestaurantRequest;

import java.util.List;

public interface IRestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);
    public Restaurant updateRestaurant(Long id, CreateRestaurantRequest req) throws Exception;
    public void deleteRestaurant(Long id) throws Exception;
    public List<Restaurant> getAllRestaurants();
    public List<Restaurant> searchRestaurants(String keyword);
    public Restaurant getRestaurant(Long id) throws Exception;
    public Restaurant getRestaurantByUserId(Long id) throws Exception;
    public RestaurantDto addToFavourites(Long id, User user) throws Exception;
    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
