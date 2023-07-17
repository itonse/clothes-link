package com.itonse.clotheslink.product.repository;

import com.itonse.clotheslink.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    List<Product> findProductsByCategoryIdAndDeletedFalse(Long id, Pageable pageable);

    List<Product> findAllByName(String name, Pageable pageable);
}
