package com.itonse.clotheslink.product.repository;

import com.itonse.clotheslink.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
