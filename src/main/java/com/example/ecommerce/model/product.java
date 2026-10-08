package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
public class product {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private long id;
 private String name;
 private Double price;
 private String category;
 private String description;
 @Lob
 @Column(columnDefinition = "LONGBLOB")
 private byte[] image;

 private String imageType;
}
