package com.reselling.Book.service.sellerService;

import com.reselling.Book.model.details.Seller;
import com.reselling.Book.repo.SellerRepo;
import com.reselling.Book.service.JwtService;
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
public class SellerService {

    @Autowired
    SellerRepo repo;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtService jwtService;

    private final BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);

    public void registerSeller(Seller seller){

        try{
            if(repo.existsByEmail(seller.getEmail())){
                throw new IllegalArgumentException("Email already Registered!");
            }
            seller.setPassword(encoder.encode(seller.getPassword()));
            repo.save(seller);
        }
        catch(DataAccessException ex){
            throw new RuntimeException("DataBase error:"+ex.getMessage());
        }
    }

    public String loginSeller(String email, String password){
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            return jwtService.generateToken(email,"SELLER");

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
