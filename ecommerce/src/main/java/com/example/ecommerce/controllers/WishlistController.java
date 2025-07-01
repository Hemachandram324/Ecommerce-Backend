package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.security.services.UserDetailsImpl;
import com.example.ecommerce.security.services.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    // ✅ Get the currently logged-in user's ID
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("User is not authenticated");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }

    // ✅ Add product to wishlist
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToWishlist(@PathVariable Long productId) {
        wishlistService.addToWishlist(getCurrentUserId(), productId);
        return ResponseEntity.ok("Product added to wishlist.");
    }

    // ✅ Remove product from wishlist
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long productId) {
        wishlistService.removeFromWishlist(getCurrentUserId(), productId);
        return ResponseEntity.ok("Product removed from wishlist.");
    }

    // ✅ Get all wishlist products for the current user
    @GetMapping("/list")
    public ResponseEntity<Set<ProductDto>> getWishlist() {
        Set<Product> products = wishlistService.getWishlistProducts(getCurrentUserId());
        Set<ProductDto> dtos = products.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(dtos);
    }

    // ✅ Convert Product to ProductDto
    private ProductDto toDto(Product p) {
        return new ProductDto(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getBrand(),
                p.getCategory().getName(),
                p.getImageFilename() // ✅ Direct Cloudinary URL or saved image URL
        );
    }

    // ✅ DTO record class for Wishlist Products
    public record ProductDto(
            Long id,
            String name,
            String description,
            BigDecimal price,
            String brand,
            String category,
            String imageUrl
    ) {}
}
