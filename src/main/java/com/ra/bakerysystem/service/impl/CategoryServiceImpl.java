package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.model.DTO.CategoryDTO;
import com.ra.bakerysystem.model.entity.Category;
import com.ra.bakerysystem.repository.CategoryRepository;
import com.ra.bakerysystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Lấy danh sách tất cả Category
     * Luồng xử lý:
     *  - Lấy danh sách Category entity từ database
     *  - Map từng entity sang CategoryDTO
     *  - Trả về danh sách DTO cho controller
     * @return List<CategoryDTO> danh sách category
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDTO::new)
                .toList();
    }
}