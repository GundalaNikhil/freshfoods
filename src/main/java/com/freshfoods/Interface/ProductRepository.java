package com.freshfoods.Interface;

import com.freshfoods.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActiveTrue();
    Optional<Product> findBySkuAndIsActiveTrue(String sku);
}
