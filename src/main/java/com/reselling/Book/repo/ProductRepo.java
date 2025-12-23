package com.reselling.Book.repo;

import com.reselling.Book.dto.ProductDTO;
import com.reselling.Book.model.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {
    @Query("""
SELECT new com.reselling.Book.dto.ProductDTO(
    p.id,
    p.name,
    p.category,
    p.price,
    CAST(p.condition AS string),
    p.description,
    p.imageName
)
FROM Product p
WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
   OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    List<ProductDTO> searchByKeyword(@Param("keyword") String keyword);

}
