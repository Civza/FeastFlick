package com.feastflick.controller;

import com.feastflick.model.Cart;
import com.feastflick.model.CartItem;
import com.feastflick.request.AddCartItemRequest;
import com.feastflick.request.UpdateCartItemRequest;
import com.feastflick.service.ICartService;
import com.feastflick.service.IUserService;
import com.feastflick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IUserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request, @RequestHeader("Authorization") String token) throws Exception {
        CartItem cartItem = cartService.addItemToCart(request, token);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request, @RequestHeader("Authorization") String token) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, token);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String token) throws Exception {
        Cart cart = cartService.clearCart(userService.findUserByToken(token).getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String token) throws Exception {
        Cart cart = cartService.findCartByUserId(userService.findUserByToken(token).getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
