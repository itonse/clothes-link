package com.itonse.clotheslink.product.domain;

import com.itonse.clotheslink.common.BaseEntity;
import com.itonse.clotheslink.user.domain.Seller;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(indexes = {
        @Index(name = "idx_product_createdAt", columnList = "createdAt"),
        @Index(name = "idx_product_category_id", columnList = "category_id")
})
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int price;
    private int stock;
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
