package com.itonse.clotheslink.product.service.Impl;

import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.product.domain.Category;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.dto.ProductDto;
import com.itonse.clotheslink.product.dto.ProductSummaryInfo;
import com.itonse.clotheslink.product.dto.UpdateProductDto;
import com.itonse.clotheslink.product.repository.CategoryRepository;
import com.itonse.clotheslink.product.repository.ProductRepository;
import com.itonse.clotheslink.product.service.ProductService;
import com.itonse.clotheslink.user.domain.Seller;
import com.itonse.clotheslink.user.service.SellerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.itonse.clotheslink.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SellerAuthService sellerAuthService;

    @Override
    @Transactional
    public ProductSummaryInfo addProduct(String token, ProductDto dto) {
        Seller seller = validateSeller(token);

        Category category = categoryRepository.findByName(dto.getCategory())
                .orElseThrow(() -> new CustomException(NOT_EXISTS_CATEGORY));

        Product product = ProductDto.toEntity(dto);
        product.setCategory(category);
        product.setSeller(seller);
        productRepository.save(product);

        return ProductSummaryInfo.builder()
                .categoryId(product.getCategory().getId())
                .productId(product.getId())
                .sellerId(product.getSeller().getId())
                .productName(product.getName())
                .build();
    }

    @Override
    @Transactional
    public ProductSummaryInfo updateProduct(String token, Long productId, UpdateProductDto dto) {
        Product product = validateUpdateProduct(token, productId);

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setDeleted(dto.isDeleted());

        return ProductSummaryInfo.builder()
                .categoryId(product.getCategory().getId())
                .productId(product.getId())
                .sellerId(product.getSeller().getId())
                .productName(product.getName())
                .build();
    }

    @Override
    public Seller validateSeller(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(INVALID_TOKEN);
        }

        Seller seller = sellerAuthService.findSellerByToken(token);

        if (!seller.isAuthenticated()) {
            throw new CustomException(UNAUTHORIZED_USER);
        }

        return seller;
    }

    @Override
    public Product validateUpdateProduct(String token, Long productId) {
        Seller seller = validateSeller(token);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(NOT_EXISTS_PRODUCT));

        if (!product.getSeller().equals(seller)) {
            throw new CustomException(NOT_SELLERS_PRODUCT);
        }

        return product;
    }

}
