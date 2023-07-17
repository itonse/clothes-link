package com.itonse.clotheslink.product.domain;

import com.itonse.clotheslink.common.BaseEntity;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.exception.ErrorCode;
import com.itonse.clotheslink.user.domain.Seller;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int price;
    private int stock;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void reduceStock(int count) {
        if (this.stock - count < 0) {
            throw new CustomException(ErrorCode.EXCEEDED_STOCK_QUANTITY);
        }
        this.stock -= count;
    }
}
