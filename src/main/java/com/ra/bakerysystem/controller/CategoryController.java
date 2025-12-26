package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.CategoryDTO;
import com.ra.bakerysystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Hiển thị tất cả các Categories
    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.findAll();
    }
}

