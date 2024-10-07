package com.feastflick.repository;

import com.feastflick.model.Cart;
import com.feastflick.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
