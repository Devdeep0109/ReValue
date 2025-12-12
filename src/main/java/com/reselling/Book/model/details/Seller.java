package com.reselling.Book.model.details;

import com.reselling.Book.model.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    private String address;

    private String profileImage;  // optional URL or stored filename

    private double rating = 0.0;

    private int totalSales = 0;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private  List<Product> products;
}
