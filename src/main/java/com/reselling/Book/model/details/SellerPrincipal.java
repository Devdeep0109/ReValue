package com.reselling.Book.model.details;


import com.reselling.Book.model.details.Seller;  // adjust package if needed
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SellerPrincipal implements UserDetails {

    private final Seller seller;

    public SellerPrincipal(Seller seller) {
        this.seller = seller;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE for seller
        return Collections.singleton(new SimpleGrantedAuthority("SELLER"));
        // OR return List.of(new SimpleGrantedAuthority("ROLE_SELLER"));
    }

    @Override
    public String getPassword() {
        return seller.getPassword();
    }

    @Override
    public String getUsername() {
        return seller.getEmail();  // email as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

