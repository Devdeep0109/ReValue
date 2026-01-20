package com.reselling.Book.controller;

import com.reselling.Book.dto.PlaceOrderRequest;
import com.reselling.Book.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    private ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest request){
        orderService.placeOrder(request);
        return ResponseEntity.ok("Order placed successfully");
    }
    @GetMapping("/allorders")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrdersForUser());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
