package com.feastflick.service;

import com.feastflick.dto.RestaurantDto;
import com.feastflick.model.Address;
import com.feastflick.model.Restaurant;
import com.feastflick.model.User;
import com.feastflick.repository.AddressRepository;
import com.feastflick.repository.RestaurantRepository;
import com.feastflick.repository.UserRepository;
import com.feastflick.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService implements IRestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setImages(req.getImages());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long id, CreateRestaurantRequest req) throws Exception {
        Restaurant restaurant = getRestaurant(id);

        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(req.getCuisineType());
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(req.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(req.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) throws Exception {
        Restaurant restaurant = getRestaurant(id);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant getRestaurant(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found with id " + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(id);

        if (restaurant == null) {
            throw new Exception("Restaurant not found with owner id " + id);
        }

        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long id, User user) throws Exception {
        Restaurant restaurant = getRestaurant(id);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(id);
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());

        boolean isFavorite = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto fav : favorites) {
            if (fav.getId().equals(id)) {
                isFavorite = true;
                break;
            }
        }

        if (isFavorite) {
            favorites.removeIf(favorite -> favorite.getId().equals(id));
        } else {
            favorites.add(restaurantDto);
        }

        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = getRestaurant(id);

        if (!restaurant.isOpen()){
            restaurant.setOpen(true);
        } else {
            restaurant.setOpen(false);
        }

        return restaurantRepository.save(restaurant);
    }
}
