package com.feastflick.service;

import com.feastflick.model.*;
import com.feastflick.repository.CartItemRepository;
import com.feastflick.repository.CartRepository;
import com.feastflick.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService{
    @Autowired
    CartRepository cartRepository;

    @Autowired
    IUserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private IFoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String token) throws Exception {
        User user = userService.findUserByToken(token);

        Food food = foodService.findFoodById(request.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItem()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem cartItem = new CartItem();

        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setIngredients(request.getIngredients());
        cartItem.setTotalPrice(request.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()) throw new Exception("Cart Item not found");

        CartItem cartItem = cartItemOptional.get();

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String token) throws Exception {
        User user = userService.findUserByToken(token);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()) throw new Exception("Cart Item not found");

        CartItem item = cartItemOptional.get();

        cart.getItem().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total = 0L;

        for (CartItem cartItem : cart.getItem()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findById(id);

        if (cartOptional.isEmpty()) throw new Exception("Cart not found with id: " + id);

        return cartOptional.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));

        return cartRepository.save(cart);
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
