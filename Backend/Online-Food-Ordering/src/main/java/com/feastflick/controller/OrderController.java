package com.feastflick.controller;

import com.feastflick.model.Order;
import com.feastflick.request.OrderRequest;
import com.feastflick.service.IOrderService;
import com.feastflick.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request, @RequestHeader("Authorization") String token) throws Exception {

        Order order = orderService.createOrder(request, userService.findUserByToken(token));

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestBody OrderRequest request, @RequestHeader("Authorization") String token) throws Exception {

        List<Order> orders = orderService.getUserOrders(userService.findUserByToken(token).getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
