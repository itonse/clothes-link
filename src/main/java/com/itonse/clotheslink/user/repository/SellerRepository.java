package com.itonse.clotheslink.user.repository;

import com.itonse.clotheslink.user.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByEmail(String email);

    Optional<Seller> findByEmailAndPassword(String email, String password);

    Optional<Seller> findByIdAndEmail(Long id, String email);
}
