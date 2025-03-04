package com.youssef_gamal.database_locking.controllers;

import com.youssef_gamal.database_locking.dtos.ProductDto;
import com.youssef_gamal.database_locking.mappers.ProductMapper;
import com.youssef_gamal.database_locking.models.Product;
import com.youssef_gamal.database_locking.services.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Received request to create product: {}", productDto);
        Product product = productMapper.toEntity(productDto);
        Product createdProduct = productService.createProduct(product);
        ProductDto createdProductDto = productMapper.toDto(createdProduct);
        log.info("Created product: {}", createdProductDto);
        return ResponseEntity.ok(createdProductDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        log.info("Received request to fetch product with id: {}", id);
        Product product = productService.getProductById(id);
        ProductDto productDto = productMapper.toDto(product);
        log.info("Fetched product: {}", productDto);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info("Received request to fetch all products");
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = products.stream()
                                               .map(productMapper::toDto)
                                               .collect(Collectors.toList());
        log.info("Fetched all products");
        return ResponseEntity.ok(productDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @Valid @RequestBody ProductDto productDto) {
        log.info("Received request to update product with id: {} to new data: {}", id, productDto);
        Product product = productMapper.toEntity(productDto);
        product.setId(id);
        Product updatedProduct = productService.updateProduct(id, product.getPrice());
        ProductDto updatedProductDto = productMapper.toDto(updatedProduct);
        log.info("Updated product: {}", updatedProductDto);
        return ResponseEntity.ok(updatedProductDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        log.info("Received request to delete product with id: {}", id);
        productService.deleteProduct(id);
        log.info("Deleted product with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}