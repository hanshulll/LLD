package com.example.LLD.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    // unique productId for a product
    private String productId;
    // quantity of the product
    private int quantity;
}
