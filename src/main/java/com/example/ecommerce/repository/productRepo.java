package com.example.ecommerce.repository;

import com.example.ecommerce.model.product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface productRepo extends JpaRepository<product,Long> {
    List<product> findByCategoryIgnoreCase(String category);
}
