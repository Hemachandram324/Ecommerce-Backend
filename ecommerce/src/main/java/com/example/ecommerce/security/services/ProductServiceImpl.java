package com.example.ecommerce.security.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final Cloudinary cloudinary;

    @Override
    public Product addProduct(Product product, MultipartFile image) {
        handleImageUpload(product, image);
        return repo.save(product);
    }

    @Override
    public List<Product> getAll() {
        return repo.findAll();
    }

    @Override
    public Product getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    @Override
    public List<Product> getByName(String name) {
        return repo.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> getByCategoryName(String categoryName) {
        return repo.findAllByCategory_Name(categoryName);
    }

    @Override
    public Product updateByName(String name, Product changes, MultipartFile image) {
        Product existing = getByExactName(name);
        if (changes.getName() != null) existing.setName(changes.getName());
        if (changes.getDescription() != null) existing.setDescription(changes.getDescription());
        if (changes.getBrand() != null) existing.setBrand(changes.getBrand());
        if (changes.getPrice() != null) existing.setPrice(changes.getPrice());
        if (changes.getCategory() != null) existing.setCategory(changes.getCategory());
        if (image != null && !image.isEmpty()) handleImageUpload(existing, image);
        return repo.save(existing);
    }

    @Override
    public Product updateImageByName(String name, MultipartFile image) {
        Product p = getByExactName(name);
        handleImageUpload(p, image);
        return repo.save(p);
    }

    @Override
    public Product updateFieldByName(String name, String fieldName, Object value) {
        Product p = getByExactName(name);
        switch (fieldName) {
            case "name" -> p.setName((String) value);
            case "description" -> p.setDescription((String) value);
            case "price" -> p.setPrice((java.math.BigDecimal) value);
            case "brand" -> p.setBrand((String) value);
            case "category" -> p.setCategory((com.example.ecommerce.models.Category) value);
            default -> throw new IllegalArgumentException("Unsupported field: " + fieldName);
        }
        return repo.save(p);
    }

    @Override
    public void deleteByName(String name) {
        Product p = getByExactName(name);
        repo.delete(p);
    }

    @Override
    public Product getByExactName(String name) {
        return repo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + name));
    }

    // Upload Image to Cloudinary
    private void handleImageUpload(Product product, MultipartFile image) {
        if (image == null || image.isEmpty()) return;
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
                    ObjectUtils.asMap("folder", "ecommerce_products"));
            String url = (String) uploadResult.get("secure_url");
            product.setImageFilename(url);
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
}
