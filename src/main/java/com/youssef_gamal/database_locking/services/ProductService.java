package com.youssef_gamal.database_locking.services;

import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youssef_gamal.database_locking.models.Product;
import com.youssef_gamal.database_locking.repos.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product updateProduct(String id, double newPrice) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setPrice(newPrice);
        return productRepository.save(product);  // Version check happens here
    }

    // Handle Optimistic Locking Exception
    @Transactional
    public Product updateProductWithRetry(String id, double newPrice) {
        int retries = 3; // Retry logic
        for (int i = 0; i < retries; i++) {
            try {
                return updateProduct(id, newPrice);
            } catch (OptimisticLockException | OptimisticLockingFailureException e) {
                if (i == retries - 1) {
                    throw new RuntimeException("Update failed due to concurrent modification");
                }
            }
        }
        return null;
    }
}
