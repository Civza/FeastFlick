package com.feastflick.service;

import com.feastflick.model.Category;

import java.util.List;

public interface ICategoryService {
    public Category createCategory(String categoryName, Long userId) throws Exception;
    public List<Category> findCategoriesByRestaurantId(Long userId) throws Exception;
    public Category findCategoryById(Long categoryId) throws Exception;

}
