package com.itonse.clotheslink.cart.repository;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.user.domain.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

        boolean existsByProductAndCustomer(Product product, Customer customer);

        Optional<Cart> findByProductAndCustomer(Product product, Customer customer);

        Optional<Cart> findByIdAndCustomer(Long id, Customer customer);

        List<Cart> findCartsByCustomer(Customer customer, Pageable pageable);
}
