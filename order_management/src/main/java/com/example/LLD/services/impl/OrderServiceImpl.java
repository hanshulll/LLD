package com.example.LLD.services.impl;

import com.example.LLD.entities.Order;
import com.example.LLD.entities.OrderItem;
import com.example.LLD.enums.OrderStatus;
import com.example.LLD.repository.OrderRepository;
import com.example.LLD.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }


    @Override
    public Order createOrder(List<OrderItem> items) {
        Order order = new Order(items);
        return this.repository.save(order);
    }

    @Override
    public Order getOrder(String orderId) {
        return this.repository.findById(orderId);
    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus status) {
        this.repository.updateStatus(orderId,status);
    }
}
