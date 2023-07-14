package com.itonse.clotheslink.product.domain;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Mapping(mappingPath = "/elasticsearch/product-mapping.json")
@Setting(settingPath = "/elasticsearch/product-setting.json")
@Document(indexName = "product_index")
public class ProductDocument {

    @Id
    Long id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    private int price;
    private int stock;
    private boolean deleted;

}
