package com.example.LLD.services;

import com.example.LLD.entities.Order;
import com.example.LLD.entities.OrderItem;
import com.example.LLD.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(List<OrderItem> items);
    Order getOrder(String orderId);
    void updateOrderStatus(String orderId, OrderStatus status);
}
