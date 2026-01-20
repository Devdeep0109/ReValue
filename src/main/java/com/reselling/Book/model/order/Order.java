package com.reselling.Book.model.order;

import com.reselling.Book.model.details.User;
import com.reselling.Book.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(User user){
        this.user = user;
        this.status = OrderStatus.PLACED;
        this.createdAt = LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item){
        items.add(item);
        item.setOrder(this);
    }

}
