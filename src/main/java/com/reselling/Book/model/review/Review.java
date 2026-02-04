package com.reselling.Book.model.review;

import com.reselling.Book.model.details.Seller;
import com.reselling.Book.model.details.User;
import com.reselling.Book.model.enums.Conditions;
import com.reselling.Book.model.order.OrderItem;
import com.reselling.Book.model.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private OrderItem orderItem;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Seller seller;

    // authenticity inputs
    @Enumerated(EnumType.STRING)
    private Conditions sellerClaimedCondition;

    @Enumerated(EnumType.STRING)
    private Conditions customerReceivedCondition;

    // ratings
    private int productRating;  //per purchase
    private double sellerRating;

    private LocalDateTime createdAt;
}
