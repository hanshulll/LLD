package com.example.LLD.entities;

import com.example.LLD.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {
    private final String orderId;
    private List<OrderItem> items;
    private OrderStatus status;

    public Order(List<OrderItem> items) {
        this.items = items;
        this.orderId = UUID.randomUUID().toString();
        this.status = OrderStatus.CREATED;
    }
}
