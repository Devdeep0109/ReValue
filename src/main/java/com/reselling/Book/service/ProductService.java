package com.reselling.Book.service;

import com.reselling.Book.model.products.Product;
import com.reselling.Book.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getAllProducts() {
        try{
            return repo.findAll();
        }
        catch (DataAccessException e){
            throw new RuntimeException("Unable to fetch: "+ e.getMessage());
        }
    }

    public void addProduct(Product product, MultipartFile image) {

        try{
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

    public Product getProductById(int id) {
        try{
            return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product Id Invalid!"));
        }
        catch (DataAccessException e){
            throw new RuntimeException("Unable to fetch Data: "+ e.getMessage());
        }
    }

    public List<Product> searchByKeyword(String keyword) {

        try{
            List<Product> products = repo.searchByKeyword(keyword);
            if (products.isEmpty()) {
                throw new IllegalArgumentException("No products found matching keyword: " + keyword);
            }
            return products;
        }
        catch (DataAccessException e){
            throw new RuntimeException("Unable to fetch Data: "+ e.getMessage());
        }
    }
}
