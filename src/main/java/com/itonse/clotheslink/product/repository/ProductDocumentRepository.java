package com.itonse.clotheslink.product.repository;

import com.itonse.clotheslink.product.domain.ProductDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, Long> {

    @Query("{\"bool\":{\"must\":{\"multi_match\":{\"query\":\"?0\", \"fields\":[\"name\", \"description\"]}}}}")
    List<ProductDocument> searchByKeyword(String keyword, Pageable pageable);
}
