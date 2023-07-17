package com.itonse.clotheslink.product.service.Impl;

import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.domain.ProductDocument;
import com.itonse.clotheslink.product.dto.ConvertProduct;
import com.itonse.clotheslink.product.dto.ProductDetail;
import com.itonse.clotheslink.product.dto.ProductDocumentDto;
import com.itonse.clotheslink.product.repository.CategoryRepository;
import com.itonse.clotheslink.product.repository.ProductDocumentRepository;
import com.itonse.clotheslink.product.repository.ProductRepository;
import com.itonse.clotheslink.product.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.itonse.clotheslink.exception.ErrorCode.NOT_EXISTS_CATEGORY;
import static com.itonse.clotheslink.exception.ErrorCode.NOT_EXISTS_PRODUCT;

@RequiredArgsConstructor
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductDocumentRepository productDocumentRepository;

    private static final int PAGE_SIZE = 5;

    @Override
    public List<ProductDetail> getRecentByCategory(Long id, int page) {

        if (!categoryRepository.existsById(id)) {
            throw new CustomException(NOT_EXISTS_CATEGORY);
        }

        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Product> products =
                productRepository.findProductsByCategoryIdAndDeletedFalse(
                        id, pageable);

        if (products.isEmpty()) {
            throw new CustomException(NOT_EXISTS_PRODUCT);
        }

        return products.stream()
                .map(ConvertProduct.toProductDetail::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDetail> getRecentByName(String name, int page) {

        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Product> products =
                productRepository.findAllByName(name, pageable);

        if (products.isEmpty()) {
            throw new CustomException(NOT_EXISTS_PRODUCT);
        }

        return products.stream()
                .map(ConvertProduct.toProductDetail::from)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "ProductDetail", key = "#id")
    public ProductDetail getProductDetail(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_EXISTS_PRODUCT));

        return ConvertProduct.toProductDetail.from(product);
    }

    @Override
    public List<ProductDetail> getProductsByKeyword(String keyword, int page) {

        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE);

        List<ProductDocument> productDocuments =
                productDocumentRepository.searchByKeyword(keyword, pageable);

        if (productDocuments.isEmpty()) {
            throw new CustomException(NOT_EXISTS_PRODUCT);
        }

        return productDocuments.stream()
                .map(ProductDocumentDto::toProductDetail)
                .collect(Collectors.toList());
    }
}
