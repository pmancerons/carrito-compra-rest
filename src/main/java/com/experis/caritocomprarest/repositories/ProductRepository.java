package com.experis.caritocomprarest.repositories;

import com.experis.caritocomprarest.data.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByNameContains(String productName, Pageable pageable);
    Page<Product> findAllByBrand(String productBrand, Pageable pageable);
    Page<Product> findAllByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);
}
