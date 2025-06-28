package com.example.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    private BigDecimal price;

    private String brand;

    @Column(length = 500)
    private String imageFilename; // Cloudinary URL

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
