package com.reselling.Book.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    private List<OrderItemRequest> items;

}
