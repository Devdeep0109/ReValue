package com.reselling.Book.service;

import com.reselling.Book.model.details.User;
import com.reselling.Book.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    public UserRepo repo;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtService jwtService;

    private final BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);

    public void registerUser(User user){

        try{
            if(repo.existsByEmail(user.getEmail())){
                throw new IllegalArgumentException("Email already Registered!");
            }
            user.setPassword(encoder.encode(user.getPassword()));
            repo.save(user);
        }
        catch(DataAccessException ex){
            throw new RuntimeException("DataBase error:"+ex.getMessage());
        }
    }

    public String loginUser(String email, String password){
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // If authentication passed, Spring guarantees it's authenticated
            return jwtService.generateToken(email);

        } catch (AuthenticationException ex) {
            // Bad credentials
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
