package com.itonse.clotheslink.product.service;

public interface CategoryService {
    String addCategory(String categoryName);

    void validateCategoryName(String categoryName);
}
