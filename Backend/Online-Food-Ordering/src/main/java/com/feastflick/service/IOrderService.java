package com.feastflick.service;

import com.feastflick.model.Order;
import com.feastflick.model.User;
import com.feastflick.request.OrderRequest;

import java.util.List;

public interface IOrderService {
    public Order createOrder(OrderRequest order, User user) throws Exception;
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getUserOrders(Long userId) throws Exception;
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;
    public Order findOrderById(Long orderId) throws Exception;
}
