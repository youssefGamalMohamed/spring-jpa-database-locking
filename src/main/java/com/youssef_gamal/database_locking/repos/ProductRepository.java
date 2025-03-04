package com.youssef_gamal.database_locking.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.youssef_gamal.database_locking.models.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
