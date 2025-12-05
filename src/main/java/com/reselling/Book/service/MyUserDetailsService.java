package com.reselling.Book.service;

import com.reselling.Book.model.details.User;
import com.reselling.Book.model.details.UserPrincipal;
import com.reselling.Book.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("email:      " + email);
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user: 404"));

        System.out.println("✅ Found user in DB: " + user.getEmail());

        return new UserPrincipal(user);
    }

}
