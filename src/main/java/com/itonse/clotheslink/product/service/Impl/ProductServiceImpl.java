package com.itonse.clotheslink.product.service.Impl;

import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.product.domain.Category;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.dto.ProductDto;
import com.itonse.clotheslink.product.dto.ProductResponse;
import com.itonse.clotheslink.product.repository.CategoryRepository;
import com.itonse.clotheslink.product.repository.ProductRepository;
import com.itonse.clotheslink.product.service.ProductService;
import com.itonse.clotheslink.seller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.NOT_EXISTS_CATEGORY;
import static com.itonse.clotheslink.exception.ErrorCode.UNAUTHORIZED_USER;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final TokenService tokenService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse addProduct(String token, ProductDto dto) {
        Seller seller = validateSeller(token);

        Category category = categoryRepository.findByName(dto.getCategory())
                .orElseThrow(() -> new CustomException(NOT_EXISTS_CATEGORY));

        Product product = ProductDto.toEntity(dto);
        product.setCategory(category);
        product.setSeller(seller);
        productRepository.save(product);

        return ProductResponse.builder()
                .categoryId(product.getCategory().getId())
                .productId(product.getId())
                .sellerId(product.getSeller().getId())
                .productName(product.getName())
                .build();
    }

    @Override
    public Seller validateSeller(String token) {
        tokenService.validateToken(token);

        Seller seller = tokenService.findSellerByToken(token);

        if (!seller.isAuthenticated()) {
            throw new CustomException(UNAUTHORIZED_USER);
        }

        return seller;
    }
}
