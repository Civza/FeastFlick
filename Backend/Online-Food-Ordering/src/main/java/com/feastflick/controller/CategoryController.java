package com.feastflick.controller;

import com.feastflick.model.Category;
import com.feastflick.model.User;
import com.feastflick.service.CategoryService;
import com.feastflick.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IUserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);

        Category createdCategory = categoryService.createCategory(category.getName(), category.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategories(@RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByToken(token);

        List<Category> categories = categoryService.findCategoriesByRestaurantId(user.getId());

        return new ResponseEntity<>(categories, HttpStatus.CREATED);
    }


}
