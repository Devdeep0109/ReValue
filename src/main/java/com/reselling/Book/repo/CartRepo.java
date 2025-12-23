package com.reselling.Book.repo;

import com.reselling.Book.model.cart.Carts;
import com.reselling.Book.model.details.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Carts,Long> {

    Optional<Carts> findByUser(User user);

    boolean existsByUser(User user);
}
