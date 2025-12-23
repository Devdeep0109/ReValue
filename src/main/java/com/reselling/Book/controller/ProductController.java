package com.reselling.Book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reselling.Book.dto.ProductDTO;
import com.reselling.Book.model.products.Product;
import com.reselling.Book.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/allProducts")
    public ResponseEntity<?> getAllProducts(){

        try{
            List<ProductDTO> products = service.getAllProducts();
            return ResponseEntity.ok(products);

        }
        catch(RuntimeException ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping(value = "/addproduct" ,consumes = {"multipart/form-data"})
    public ResponseEntity<String> addProduct(@RequestPart("product") String productJson, @RequestPart("image")MultipartFile image){

        try{
            //  Convert JSON string to Product object manually
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson, Product.class);
            service.addProduct(product,image);
            return ResponseEntity.ok("Product added successfully");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

        @GetMapping("/searchById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){

        try{
            Product p = service.getProductById(id);
            ProductDTO dto = new ProductDTO(
                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getPrice(),
                    p.getCondition().toString(),
                    p.getDescription(),
                    p.getImageName()
            );

            return ResponseEntity.ok(dto);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/searchByKeyword")
    public ResponseEntity<?> getProductByKeyword(@RequestParam String keyword){

        try{
            List<ProductDTO> products = service.searchByKeyword(keyword);
            return ResponseEntity.ok().body(products);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/product/image/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Product product = service.getProductById(id);

        return ResponseEntity.ok()
                .header("Content-Type", product.getImageType())
                .body(product.getImageData());
    }


}
