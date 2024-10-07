package com.feastflick.service;

import com.feastflick.model.Cart;
import com.feastflick.model.CartItem;
import com.feastflick.request.AddCartItemRequest;

public interface ICartService {
    public CartItem addItemToCart(AddCartItemRequest request, String token) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String token) throws Exception;

    public Long calculateCartTotal(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;
}
