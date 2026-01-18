package com.example.LLD.repository;

import com.example.LLD.entities.Order;
import com.example.LLD.enums.OrderStatus;

public interface OrderRepository {
    Order save(Order order);
    Order findById(String orderId);
    void updateStatus(String orderId, OrderStatus status);
}
