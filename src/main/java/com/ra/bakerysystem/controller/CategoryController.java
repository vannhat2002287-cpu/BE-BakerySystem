package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.entity.Category;
import com.ra.bakerysystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // GET: lấy tất cả category
    @GetMapping("/all")
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    // GET: lấy category theo id
    @GetMapping("/{id}")
    public Category getById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    // POST: tạo category
    @PostMapping ("/add-category")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }
}
