package com.itonse.clotheslink.cart.domain;

import com.itonse.clotheslink.common.BaseEntity;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.user.domain.Customer;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
