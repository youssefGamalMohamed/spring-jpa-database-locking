package com.youssef_gamal.database_locking.services;

import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youssef_gamal.database_locking.models.Product;
import com.youssef_gamal.database_locking.repos.ProductRepository;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product);
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product getProductById(String id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        products.forEach(product -> log.info("\t Fetched product: {}", product));
        return products;
    }

    @Transactional
    public Product updateProduct(String id, double newPrice) {
        log.info("Updating product with id: {} to new price: {}", id, newPrice);
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setPrice(newPrice);
        return productRepository.save(product);  // Version check happens here
    }

    // Handle Optimistic Locking Exception
    @Transactional
    public Product updateProductWithRetry(String id, double newPrice) {
        log.info("Updating product with id: {} to new price: {} with retry logic", id, newPrice);
        int retries = 3; // Retry logic
        for (int i = 0; i < retries; i++) {
            try {
                return updateProduct(id, newPrice);
            } catch (OptimisticLockException | OptimisticLockingFailureException e) {
                log.warn("Optimistic locking exception on attempt {} for product with id: {}", i + 1, id);
                if (i == retries - 1) {
                    log.error("Update failed due to concurrent modification for product with id: {}", id);
                    throw new RuntimeException("Update failed due to concurrent modification");
                }
            }
        }
        return null;
    }

    @Transactional
    public void deleteProduct(String id) {
        log.info("Deleting product with id: {}", id);
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}