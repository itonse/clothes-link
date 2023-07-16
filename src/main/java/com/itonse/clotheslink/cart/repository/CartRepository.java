package com.itonse.clotheslink.cart.repository;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.user.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

        boolean existsByProductAndCustomer(Product product, Customer customer);
}
