package com.example.LLD.repository.impl;

import com.example.LLD.entities.Order;
import com.example.LLD.enums.OrderStatus;
import com.example.LLD.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final Map<String,Order> orderStore = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        this.orderStore.put(order.getOrderId(),order);
        return order;
    }

    @Override
    public Order findById(String orderId) {
        return orderStore.get(orderId);
    }

    @Override
    public void updateStatus(String orderId, OrderStatus status) {
        Order order = this.orderStore.get(orderId);
        if (order!=null) {
            synchronized (order) {
                order.setStatus(status);
            }
        }
    }
}
