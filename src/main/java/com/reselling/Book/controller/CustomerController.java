package com.reselling.Book.controller;

import com.reselling.Book.dto.LoginRequest;
import com.reselling.Book.model.details.User;
import com.reselling.Book.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class CustomerController {

    @Autowired
    CustomerService userService;



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){

        try{
            userService.registerUser(user);
            return new ResponseEntity<>("User Added successfully!", HttpStatusCode.valueOf(200));
        }
        catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request){

        try{
            String token = userService.loginUser(request.getEmail(), request.getPassword());
//            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
            return ResponseEntity.ok(token);
        }
        catch (IllegalArgumentException | BadCredentialsException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
