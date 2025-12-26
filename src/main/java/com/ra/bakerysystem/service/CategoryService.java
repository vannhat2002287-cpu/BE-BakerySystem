package com.ra.bakerysystem.service;

import com.ra.bakerysystem.model.DTO.CategoryDTO;
import com.ra.bakerysystem.model.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);
    List<Category> findAll();
    Category findById(long id);


}
