package com.reselling.Book.repo;

import com.reselling.Book.model.details.Seller;
import com.reselling.Book.model.details.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepo extends JpaRepository<Seller,Long> {


    boolean existsByEmail(String email);
    Optional<Seller> findByEmail(String email);
}
