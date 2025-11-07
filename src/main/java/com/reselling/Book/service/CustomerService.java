package com.reselling.Book.service;

import com.reselling.Book.model.details.UserDetails;
import com.reselling.Book.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    public UserRepo repo;

    public void registerUser(UserDetails user){

        try{
            if(repo.existsByEmail(user.getEmail())){
                throw new IllegalArgumentException("Email already Registered!");
            }

            repo.save(user);
        }
        catch(DataAccessException ex){
            throw new RuntimeException("DataBase error:"+ex.getMessage());
        }
    }

    public void loginUser(UserDetails user) {

        String email = user.getEmail();
        UserDetails detail = repo.findByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("\"User not found with email: \" + email"));

        if(!detail.getPassword().equals("SecurePass123!")){
            throw new BadCredentialsException("Invalid Email or Password");
        }

    }
}
