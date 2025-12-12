package com.reselling.Book.service.sellerService;

import com.reselling.Book.model.details.Seller;
import com.reselling.Book.model.details.SellerPrincipal;
import com.reselling.Book.repo.SellerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MySellerDetailsService implements UserDetailsService {

    @Autowired
    private SellerRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Seller seller = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("seller: 404"));
        return new SellerPrincipal(seller);
    }

}
