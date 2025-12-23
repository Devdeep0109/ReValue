package com.reselling.Book.service;

import com.reselling.Book.dto.ProductDTO;
import com.reselling.Book.model.details.Seller;
import com.reselling.Book.model.products.Product;
import com.reselling.Book.repo.ProductRepo;
import com.reselling.Book.repo.SellerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    @Autowired
    private SellerRepo sellerRepo;

    public List<ProductDTO> getAllProducts() {
        try{
            List<Product> products = repo.findAll();

            return products.stream()
                    .map(p -> new ProductDTO(
                            p.getId(),
                            p.getName(),
                            p.getCategory(),
                            p.getPrice(),
                            p.getCondition().toString(),
                            p.getDescription(),
                            p.getImageName()
                    ))
                    .toList();
        }
        catch (DataAccessException e){
            throw new RuntimeException("Unable to fetch: "+ e.getMessage());
        }
    }

    public void addProduct(Product product, MultipartFile image) {

        try{
            String email = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            Seller seller = sellerRepo.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

            product.setSeller(seller);


            if(image != null && !image.isEmpty()){
                product.setImageName(image.getOriginalFilename());
                product.setImageType(image.getContentType());
                product.setImageData(image.getBytes());
            }
            repo.save(product);
        }
        catch (DataAccessException e){
            throw new RuntimeException("Database error: "+e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image: "+e);
        }
    }

    public Product getProductById(Long id) {
        try{
            return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product Id Invalid!"));
        }
        catch (DataAccessException e){
            throw new RuntimeException("Unable to fetch Data: "+ e.getMessage());
        }
    }

    public List<ProductDTO> searchByKeyword(String keyword) {
        List<ProductDTO> products = repo.searchByKeyword(keyword);

        return repo.searchByKeyword(keyword);
    }

}
