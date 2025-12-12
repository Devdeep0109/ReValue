package com.reselling.Book.controller;

import com.reselling.Book.dto.LoginRequest;
import com.reselling.Book.model.details.Seller;
import com.reselling.Book.service.sellerService.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/seller")
@RestController
public class SellerController {

    @Autowired
    SellerService service;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Seller seller){

        try{
            service.registerSeller(seller);
            return new ResponseEntity<>("Seller registered successfully!", HttpStatusCode.valueOf(200));
        }
        catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginSeller(@RequestBody LoginRequest request){

        try{
            String token = service.loginSeller(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(token);
        }
        catch (IllegalArgumentException | BadCredentialsException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
