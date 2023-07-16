package com.itonse.clotheslink.product.service.Impl;

import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.product.domain.Category;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.domain.ProductDocument;
import com.itonse.clotheslink.product.dto.*;
import com.itonse.clotheslink.product.repository.CategoryRepository;
import com.itonse.clotheslink.product.repository.ProductDocumentRepository;
import com.itonse.clotheslink.product.repository.ProductRepository;
import com.itonse.clotheslink.product.service.ProductManageService;
import com.itonse.clotheslink.user.domain.Seller;
import com.itonse.clotheslink.user.service.SellerAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.itonse.clotheslink.exception.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductManageServiceImpl implements ProductManageService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SellerAuthService sellerAuthService;
    private final ProductDocumentRepository productDocumentRepository;

    @Override
    @Transactional
    public ProductSummary addProduct(String token, ProductDto dto) {
        Seller seller = sellerAuthService.validateSeller(token);

        Category category = categoryRepository.findByName(dto.getCategory())
                .orElseThrow(() -> new CustomException(NOT_EXISTS_CATEGORY));

        Product product = ProductDto.toEntity(dto);
        product.setCategory(category);
        product.setSeller(seller);
        productRepository.save(product);
        productDocumentRepository.save(ProductDocumentDto.from(product));

        return ProductSummary.builder()
                .categoryId(product.getCategory().getId())
                .productId(product.getId())
                .sellerId(product.getSeller().getId())
                .productName(product.getName())
                .build();
    }

    @Override
    @Transactional
    @CachePut(value = "ProductDetail", key = "#id")
    public ProductDetail updateProduct(String token, Long id, UpdateProductDto dto) {
        Product product = validateUpdateProduct(token, id);

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setDeleted(dto.isDeleted());

        ProductDocument productDocument = productDocumentRepository.findById(id)
                        .orElseThrow(() -> new CustomException(NOT_EXISTS_PRODUCT));

        productDocument.setName(dto.getName());
        productDocument.setDescription(dto.getDescription());
        productDocument.setPrice(dto.getPrice());
        productDocument.setStock(dto.getStock());
        productDocument.setDeleted(dto.isDeleted());

        productDocumentRepository.save(ProductDocumentDto.from(product));

        log.info(" Product (id: {}) has been updated ", id);
        return ConvertProductToDto.toProductDetail(product);
    }

    @Override
    public Product validateUpdateProduct(String token, Long productId) {
        Seller seller = sellerAuthService.validateSeller(token);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(NOT_EXISTS_PRODUCT));

        if (!product.getSeller().equals(seller)) {
            throw new CustomException(NOT_SELLERS_PRODUCT);
        }

        return product;
    }

}
