package com.example.LLD.controller;


import com.example.LLD.entities.Order;
import com.example.LLD.entities.OrderItem;
import com.example.LLD.enums.OrderStatus;
import com.example.LLD.services.OrderService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody List<OrderItem> items) {
        Order order = this.orderService.createOrder(items);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@RequestParam String orderId) {
        Order order = this.orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateStatusForOrder(@RequestParam String orderId, @RequestParam OrderStatus orderStatus) {
        this.orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok("Order status update for order : " + orderId);
    }
}
