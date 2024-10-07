package com.feastflick.controller;

import com.feastflick.model.Order;
import com.feastflick.model.User;
import com.feastflick.request.OrderRequest;
import com.feastflick.service.IOrderService;
import com.feastflick.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getRestaurantOrders(@PathVariable Long id, @RequestHeader("Authorization") String token, @RequestParam(required = false) String orderStatus) throws Exception{
        User user = userService.findUserByToken(token);
        List<Order> restaurantOrders = orderService.getRestaurantOrders(id, orderStatus);

        return new ResponseEntity<>(restaurantOrders, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @PathVariable String orderStatus, @RequestHeader("Authorization") String token) throws Exception{
        User user = userService.findUserByToken(token);
        Order order = orderService.updateOrder(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
