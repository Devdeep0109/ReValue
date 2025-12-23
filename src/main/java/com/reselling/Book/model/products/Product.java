package com.reselling.Book.model.products;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reselling.Book.model.details.Seller;
import com.reselling.Book.model.enums.Conditions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g. "Casio Analog Watch" or "Atomic Habits"

    private String category; // e.g. "Book", "Watch", etc.

    private double price; // selling price

    @Enumerated(EnumType.STRING)
    private Conditions condition;  // e.g. "New", "Used - Like New", "Used - Good"

    private String description; // short summary about product condition


    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yy")
    private Date dom;

    private String imageName;
    private String imageType;
    @Lob
//    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

}
