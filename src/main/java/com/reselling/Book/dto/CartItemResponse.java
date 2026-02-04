package com.reselling.Book.dto;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
}
