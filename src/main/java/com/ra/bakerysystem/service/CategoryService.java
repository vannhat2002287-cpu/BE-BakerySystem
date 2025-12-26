package com.ra.bakerysystem.service;

import com.ra.bakerysystem.model.DTO.CategoryDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
}
