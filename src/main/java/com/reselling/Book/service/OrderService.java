package com.reselling.Book.service;

import com.reselling.Book.dto.OrderItemRequest;
import com.reselling.Book.dto.PlaceOrderRequest;
import com.reselling.Book.model.details.User;
import com.reselling.Book.model.order.Order;
import com.reselling.Book.model.order.OrderItem;
import com.reselling.Book.model.products.Product;
import com.reselling.Book.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private SellerRepo sellerRepo;
    @Autowired
    private CartRepo cartRepo;


    public void placeOrder(PlaceOrderRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow( ()-> new IllegalArgumentException("user not found!"));

        if(request.getItems() == null || request.getItems().isEmpty()){
            throw new IllegalArgumentException("No item in order");
        }


        Order order = new Order(user);
        double total = 0;

        for(OrderItemRequest reqItem : request.getItems()){

            Product product = productRepo.findById(reqItem.getProductId()).orElseThrow( () -> new IllegalArgumentException("Product Not found!"));
            if (reqItem.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }

            OrderItem item = new OrderItem();

            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setSeller(product.getSeller());
            item.setPriceAtOrder(product.getPrice());
            item.setQuantity(reqItem.getQuantity());

            order.addItem(item);

            total += product.getPrice()* reqItem.getQuantity();
        }

        order.setTotalAmount(total);
        orderRepo.save(order);
    }
    private void clearCartIfExists(User user){
        cartRepo.findByUser(user).ifPresent(cart -> {
           cart.getItems().clear();
           cartRepo.save(cart);
        });
    }

    public List<Order> getAllOrdersForUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return orderRepo.findByUserOrderByCreatedAtDesc(user);
    }

    public Order getOrderById(Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByEmail(email).orElseThrow( () -> new IllegalArgumentException("User not found! "));

        return orderRepo.findByIdAndUser(id,user).orElseThrow( ()-> new IllegalArgumentException("order not found or access denied!"));
    }
}
