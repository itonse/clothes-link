package com.itonse.clotheslink.product.repository;

import com.itonse.clotheslink.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
