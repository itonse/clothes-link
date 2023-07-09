package com.itonse.clotheslink.product.service.Impl;

import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.product.domain.Category;
import com.itonse.clotheslink.product.repository.CategoryRepository;
import com.itonse.clotheslink.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_CATEGORY;
import static com.itonse.clotheslink.exception.ErrorCode.EMPTY_CATEGORY_NAME;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public String addCategory(String categoryName) {
        validateCategoryName(categoryName);

        categoryRepository.save(Category.builder()
                .name(categoryName)
                .build());

        return categoryName;
    }

    @Override
    public void validateCategoryName(String categoryName) {
        if (categoryName.isEmpty()) {
            throw new CustomException(EMPTY_CATEGORY_NAME);
        }

        categoryRepository.findByName(categoryName).ifPresent( e -> {
            throw new CustomException(ALREADY_REGISTERED_CATEGORY);
        });
    }
}
