package com.reselling.Book.service;

import com.reselling.Book.dto.AddToCartRequest;
import com.reselling.Book.model.cart.CartItems;
import com.reselling.Book.model.cart.Carts;
import com.reselling.Book.model.details.User;
import com.reselling.Book.model.products.Product;
import com.reselling.Book.repo.CartItemRepo;
import com.reselling.Book.repo.CartRepo;
import com.reselling.Book.repo.ProductRepo;
import com.reselling.Book.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userrepo;

    @Autowired
    private CartItemRepo itemsRepo;

    public void addProductToCart(AddToCartRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userrepo.findByEmail(email).orElseThrow( () -> new IllegalArgumentException("User not found!"));

        Product product = productRepo.findById(request.getProductId()).orElseThrow( () -> new IllegalArgumentException("Product not found!"));

        Carts cart = cartRepo.findByUser(user).orElseGet(() -> cartRepo.save(new Carts(user)));

        CartItems existingItem = itemsRepo.findByCartAndProduct(cart,product).orElse(null);
        if(existingItem != null){
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            itemsRepo.save(existingItem);
        }
        else{
            CartItems newItem = new CartItems();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            newItem.setPriceAtAddition(product.getPrice());
            itemsRepo.save(newItem);
        }
    }
}
