package com.reselling.Book.repo;

import com.reselling.Book.model.cart.CartItems;
import com.reselling.Book.model.cart.Carts;
import com.reselling.Book.model.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItems,Long> {

    Optional<CartItems> findByCartAndProduct(Carts cart, Product product);
}
