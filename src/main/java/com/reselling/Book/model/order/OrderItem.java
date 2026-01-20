package com.reselling.Book.model.order;

import com.reselling.Book.model.details.Seller;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    private Long productId;
    private String productName;
    private double priceAtOrder;
    private int quantity;

    @ManyToOne
    private Seller seller;

}
