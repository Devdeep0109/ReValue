package com.reselling.Book.dto;

public record ProductDTO(
        Long id,
        String name,
        String category,
        double price,
        String condition,
        String description,
        String imageName
) {}

