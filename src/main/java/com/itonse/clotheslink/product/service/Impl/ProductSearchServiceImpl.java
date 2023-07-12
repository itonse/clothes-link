package com.itonse.clotheslink.product.service.Impl;

import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.exception.ErrorCode;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.dto.ConvertProductToDto;
import com.itonse.clotheslink.product.dto.ProductDetail;
import com.itonse.clotheslink.product.repository.CategoryRepository;
import com.itonse.clotheslink.product.repository.ProductRepository;
import com.itonse.clotheslink.product.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
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

    private static final int PAGE_SIZE = 5;

    @Override
    public List<ProductDetail> getRecentByCategory(String categoryName, int page) {

        if (!categoryRepository.existsByName(categoryName)) {
            throw new CustomException(NOT_EXISTS_CATEGORY);
        }

        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Product> products =
                productRepository.findProductsByCategoryNameAndDeletedFalse(
                        categoryName, pageable);

        if (products.isEmpty()) {
            throw new CustomException(NOT_EXISTS_PRODUCT);
        }

        return products.stream()
                .map(ConvertProductToDto::toProductDetail)
                .collect(Collectors.toList());
    }
}
