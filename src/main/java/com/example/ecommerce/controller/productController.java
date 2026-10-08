package com.example.ecommerce.controller;

import com.example.ecommerce.model.product;
import com.example.ecommerce.service.productService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/products")
public class productController {
    private final productService pservice;

    public productController(productService pservice) {
        this.pservice = pservice;
    }

    @GetMapping
    public ResponseEntity<List<product>> getAll(
            @RequestParam(required = false) String category
    ) {

        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(
                    pservice.getProductsByCategory(category)
            );
        }

        return ResponseEntity.ok(pservice.getall());
    }
    @GetMapping("/{id}")
    public ResponseEntity<product> getProductById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                pservice.getProductById(id)
        );
    }
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(
            @PathVariable Long id
    ) {

        product product1 = pservice.getProductById(id);

        if (product1.getImage() == null ||
                product1.getImage().length == 0) {

            return ResponseEntity.notFound().build();
        }

        MediaType mediaType;

        try {
            mediaType = MediaType.parseMediaType(
                    product1.getImageType()
            );
        } catch (Exception e) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(product1.getImage());
    }


    // ==========================================
    // CREATE PRODUCT + IMAGE
    // ==========================================

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<product> createProduct(

            @RequestParam("name")
            String name,

            @RequestParam("price")
            Double price,

            @RequestParam("category")
            String category,

            @RequestParam("description")
            String description,

            @RequestPart("image")
            MultipartFile image

    ) throws IOException {

        product product1 = new product();

        product1.setName(name);
        product1.setPrice(price);
        product1.setCategory(category);
        product1.setDescription(description);

        // Save image
        if (image != null && !image.isEmpty()) {

            product1.setImage(image.getBytes());
            product1.setImageType(image.getContentType());
        }

        product savedProduct =
                pservice.addProduct(product1);

        return new ResponseEntity<>(
                savedProduct,
                HttpStatus.CREATED
        );
    }
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<product> updateProduct(

            @PathVariable Long id,

            @RequestParam("name")
            String name,

            @RequestParam("price")
            Double price,

            @RequestParam("category")
            String category,

            @RequestParam("description")
            String description,

            @RequestPart(value = "image", required = false)
            MultipartFile image

    ) throws IOException {

        product existingProduct =
                pservice.getProductById(id);

        existingProduct.setName(name);
        existingProduct.setPrice(price);
        existingProduct.setCategory(category);
        existingProduct.setDescription(description);

        if (image != null && !image.isEmpty()) {

            existingProduct.setImage(image.getBytes());
            existingProduct.setImageType(
                    image.getContentType()
            );
        }

        product updatedProduct =
                pservice.addProduct(existingProduct);

        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<product> DelProduct(@PathVariable Long id){
        return new ResponseEntity<>(pservice.deleteProduct(id),HttpStatus.OK);
    }



}
