package com.reselling.Book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reselling.Book.dto.AddToCartRequest;
import com.reselling.Book.model.cart.CartItems;
import com.reselling.Book.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping("/addtocart")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request){
        try{
            service.addProductToCart(request);
            return  ResponseEntity.ok("Product added to cart successfully!");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException  e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
