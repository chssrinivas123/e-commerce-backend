package com.example.ecommerce.service;

import com.example.ecommerce.model.product;
import com.example.ecommerce.repository.productRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class productService {
    private final productRepo pRepo;

    public productService(productRepo pRepo) {
        this.pRepo = pRepo;
    }

    public List<product> getall() {
        return pRepo.findAll();
    }

    public product addProduct(product product1) {
        return pRepo.save(product1);
    }

    public product getProductById(Long id) {
        return pRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id)
                );
    }

    public product deleteProduct(Long id) {
        product existingProduct = pRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id)
                );

        pRepo.deleteById(id);

        return existingProduct;
    }
    public List<product> getProductsByCategory(String category) {
        return pRepo.findByCategoryIgnoreCase(category);
    }
}
